package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Comment;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.object.impl.ObjectImpl;


class CustomerManager
{
	private ObjectLayer objectLayer = null;
	private Connection   conn = null;

	public CustomerManager(Connection conn, ObjectLayer objectLayer)
	{
		this.conn = conn;
		this.objectLayer = objectLayer;
	}

	public void save(Customer customer)
		throws RARException
		{
			String               insertCSql = "insert into Users (creditCardExpiration, creditCardNumber, licenseNumber, licenseState, membershipExpiration, firstName, lastName,userName,emailAddress,createdDate,password) values (?, ?, ?, ?, ?,?,?,?,?,?,?)";
			String               updateCSql = "update Users set creditCardExpiration = ?, creditCardNumber = ?, licenseNumber = ?, licenseState = ?, membershipExpiration = ? where id = ? ";
			PreparedStatement    stmt = null;
			int                  inscnt;
			long                 CId;


			try {

				if(!customer.isPersistent())
					stmt = (PreparedStatement) conn.prepareStatement(insertCSql);
				else
					stmt = (PreparedStatement) conn.prepareStatement(updateCSql);

				if(customer.getCreditCardExpiration() != null)
					stmt.setDate(1, new java.sql.Date(customer.getCreditCardExpiration().getTime()));
				else
					throw new RARException("Customer.save: can't save a Customer: Customer undefined");

				if(customer.getCreditCardNumber() != null)
					stmt.setString(2, customer.getCreditCardNumber());
				else
					throw new RARException("customer.save: can't save a customer: Credit Card Number is not set or not persistent");

				if(customer.getLicenseNumber() != null)
					stmt.setString(3, customer.getLicenseNumber());
				else
					throw new RARException("customer.save: can't save a customer: License Number is not set or not persistent");

				if(customer.getLicenseState() != null)
					stmt.setString(4, customer.getLicenseState());
				else
					throw new RARException("customer.save: can't save a customer: License State is not set or not persistent");

				if(customer.getMemberUntil() != null)
					stmt.setDate(5, new java.sql.Date(customer.getMemberUntil().getTime()));
				else
					throw new RARException("customer.save: can't save a customer: Membership Expiration is not set or not persistent");

				if (customer.getFirstName() != null) 
					stmt.setString(6, customer.getFirstName());
				else
					throw new RARException("customer.save: can't save a customer: firstName is not set or not persistent");

				if(customer.getLastName() != null)
					stmt.setString(7, customer.getLastName());
				else
					throw new RARException("customer.save: can't save a customer: lastName is not set or not persistent");

				if(customer.getUserName() != null)
					stmt.setString(8, customer.getUserName());
				else
					throw new RARException("customer.save: can't save a customer: lastName is not set or not persistent");

				if(customer.getEmail() != null)
					stmt.setString(9, customer.getEmail());
				else
					throw new RARException("customer.save: can't save a customer: email is not set or not persistent");

				if(customer.getCreatedDate() != null)
					stmt.setDate(10, new java.sql.Date(customer.getCreatedDate().getTime()));
				else
					throw new RARException("customer.save: can't save a customer: lastName is not set or not persistent");

				if(customer.getPassword() != null)
					stmt.setString(11,customer.getPassword());
				else
					throw new RARException("customer.save: can't save a customer: password is not set or not persistent");

				if(customer.isPersistent())
					stmt.setLong(12, customer.getId());

				inscnt = stmt.executeUpdate();

				if(!customer.isPersistent()) {
					if(inscnt >= 1) {
						String sql = "select last_insert_id()";
						if(stmt.execute(sql)) {
							ResultSet r = stmt.getResultSet();
							while(r.next()) {
								CId = r.getLong(1);
								if(CId > 0)
									customer.setId(CId);
							}
						}
					}
					else
						throw new RARException("CustomerManager.save: failed to save a customer");
				}
				else {
					if(inscnt < 1)
						throw new RARException("CustomerManager.save: failed to save a customer");
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
				throw new RARException("CustomerManager.save: failed to save a Customer: " + e);
			}
		}

	public List<Customer> restore(Customer customer)
		throws RARException
		{
			String       selectCSql = "select c.creditCardExpiration, c.creditCardNumber, c.licenseNumber, c.licenseState, c.membershipExpiration " +
				" from Users c where ";
			Statement    stmt = null;
			StringBuffer query = new StringBuffer(100);
			StringBuffer condition = new StringBuffer(100);
			condition.setLength(0);
			query.append(selectCSql);

			if(customer != null) {
				if(customer.getId() >= 0){
					query.append(" and id = " + customer.getId());
				} else { 
					if(customer.getCreditCardExpiration() != null)
						condition.append(" and Credit Card Expiration = '" + customer.getCreditCardExpiration().toString() + "'");

					if(customer.getCreditCardNumber() != null)
						condition.append(" and Credit Card Number = '" + customer.getCreditCardNumber() + "'");

					if(customer.getLicenseNumber() != null)
						condition.append(" and License Number = '" + customer.getLicenseNumber() + "'");

					if(customer.getLicenseState() != null) {
						condition.append(" License State = '" + customer.getLicenseState() + "'");
					}
					if(customer.getMemberUntil() != null) {
						condition.append(" Membership Expiration = '" + customer.getMemberUntil().toString() + "'");
					}
				}
			}

			try {

				stmt = conn.createStatement();

				if(stmt.execute(query.toString())) { // statement returned a result
					ResultSet r = stmt.getResultSet();
					return (List<Customer>) new CustomerList(r, objectLayer);
				}
			}
			catch(Exception e) {      // just in case...
				throw new RARException("CustomerManager.restore: Could not restore persistent Customer object; Root cause: " + e);
			}

			throw new RARException("CustomerManager.restore: Could not restore persistent Customer object");
		}


	public Iterator<Reservation> restoreCustomerReservation(Customer customer)
		throws RARException
		{
			String selectPersonSql = "select c.firstName, c.lastName, c.userName, c.emailAddress, c.password, c.createdDate, c.userStatus, c.userType, r.customer, r,pickupTime, r.rental, r.rentalDuration, r.rentalLocation, r.vehicleType " +
				"from reservation r, customer c where r.reservationID = c.id";
			Statement    stmt = null;
			StringBuffer query = new StringBuffer(100);
			StringBuffer condition = new StringBuffer(100);
			condition.setLength(0);
			query.append(selectPersonSql);

			if(customer != null) {

				if(customer.getId() >= 0)
					query.append(" and c.id = " + customer.getId());
				else if(customer.getUserName() != null)
					query.append(" and c.userName = '" + customer.getUserName() + "'");
				else {
					if(customer.getFirstName() != null)
						condition.append(" c.firstName = '" + customer.getFirstName() + "'");
					else
						condition.append(" AND c.firstName = '" + customer.getFirstName() + "'");

					if(customer.getLastName() != null)
						condition.append(" c.lastName = '" + customer.getLastName() + "'");
					else
						condition.append(" AND c.lastName = '" + customer.getLastName() + "'");

					if(customer.getEmail() != null)
						condition.append(" c.emailAddress = '" + customer.getEmail() + "'");
					else
						condition.append(" AND c.emailAddress = '" + customer.getEmail() + "'");

					if(customer.getPassword() != null)
						condition.append(" c.password = '" + customer.getPassword() + "'");
					else
						condition.append(" AND c.password = '" + customer.getPassword() + "'");

					if(customer.getCreatedDate() != null)
						condition.append(" c.createdDate = '" + customer.getCreatedDate() + "'");
					else
						condition.append(" AND c.createdDate = '" + customer.getCreatedDate() + "'");

					if(customer.getUserStatus() != null)
						condition.append(" c.userStatus = '" + customer.getUserStatus() + "'");
					else
						condition.append(" AND c.userStatus = '" + customer.getUserStatus() + "'");

					if(condition.length() > 0) {
						query.append(condition);
					}
				}
			}

			try {

				stmt = conn.createStatement();
				if(stmt.execute(query.toString())) {
					ResultSet r = stmt.getResultSet();
					return new ReservationList(r, objectLayer);
				}
			}
			catch(Exception e) {
				throw new RARException("CustomerManager.restoreCustomerReservation: Could not restore persistent Reservation objects; Root cause: " + e);
			}

			throw new RARException("CustomerManager.restoreCustomerReservation: Could not restore persistent Reservation objects");
		}

	public List<Comment> restoreCustomerComment(Customer customer)
		throws RARException
		{
			String selectPersonSql = "select c.firstName, c.lastName, c.userName, c.emailAddress, c.password, c.createdDate, c.userStatus, c.userType, com.commentDate, com.rental, com.comment " +
				"from comment com, customer c where com.commentID = c.id";
			Statement    stmt = null;
			StringBuffer query = new StringBuffer(100);
			StringBuffer condition = new StringBuffer(100);
			condition.setLength(0);
			query.append(selectPersonSql);

			if(customer != null) {

				if(customer.getId() >= 0)
					query.append(" and c.id = " + customer.getId());
				else if(customer.getUserName() != null)
					query.append(" and c.userName = '" + customer.getUserName() + "'");
				else {
					if(customer.getFirstName() != null)
						condition.append(" c.firstName = '" + customer.getFirstName() + "'");
					else
						condition.append(" AND c.firstName = '" + customer.getFirstName() + "'");

					if(customer.getLastName() != null)
						condition.append(" c.lastName = '" + customer.getLastName() + "'");
					else
						condition.append(" AND c.lastName = '" + customer.getLastName() + "'");

					if(customer.getEmail() != null)
						condition.append(" c.emailAddress = '" + customer.getEmail() + "'");
					else
						condition.append(" AND c.emailAddress = '" + customer.getEmail() + "'");

					if(customer.getPassword() != null)
						condition.append(" c.password = '" + customer.getPassword() + "'");
					else
						condition.append(" AND c.password = '" + customer.getPassword() + "'");

					if(customer.getCreatedDate() != null)
						condition.append(" c.createdDate = '" + customer.getCreatedDate() + "'");
					else
						condition.append(" AND c.createdDate = '" + customer.getCreatedDate() + "'");

					if(customer.getUserStatus() != null)
						condition.append(" c.userStatus = '" + customer.getUserStatus() + "'");
					else
						condition.append(" AND c.userStatus = '" + customer.getUserStatus() + "'");

					if(condition.length() > 0) {
						query.append(condition);
					}
				}
			}

			try {

				stmt = conn.createStatement();
				if(stmt.execute(query.toString())) {
					ResultSet r = stmt.getResultSet();
					return (List<Comment>) new CommentList(r, (ObjectImpl) objectLayer);
				}
			}
			catch(Exception e) {
				throw new RARException("CustomerManager.restoreCustomerComment: Could not restore persistent Comment objects; Root cause: " + e);
			}

			throw new RARException("CustomerManager.restoreCustomerComment: Could not restore persistent Comment objects");
		}





	public Iterator<Rental> restoreRentalCustomer(Customer customer)
		throws RARException
		{
			String selectPersonSql = "select c.firstName, c.lastName, c.userName, c.emailAddress, c.password, c.createdDate, c.userStatus, c.userType, r.customer, r.pickupTime, r.returnTime " +
				"from rental r, customer c where r.rentalNo = c.id";
			Statement    stmt = null;
			StringBuffer query = new StringBuffer(100);
			StringBuffer condition = new StringBuffer(100);
			condition.setLength(0);
			query.append(selectPersonSql);

			if(customer != null) {

				if(customer.getId() >= 0)
					query.append(" and c.id = " + customer.getId());
				else if(customer.getUserName() != null)
					query.append(" and c.userName = '" + customer.getUserName() + "'");
				else {
					if(customer.getFirstName() != null)
						condition.append(" c.firstName = '" + customer.getFirstName() + "'");
					else
						condition.append(" AND c.firstName = '" + customer.getFirstName() + "'");

					if(customer.getLastName() != null)
						condition.append(" c.lastName = '" + customer.getLastName() + "'");
					else
						condition.append(" AND c.lastName = '" + customer.getLastName() + "'");

					if(customer.getEmail() != null)
						condition.append(" c.emailAddress = '" + customer.getEmail() + "'");
					else
						condition.append(" AND c.emailAddress = '" + customer.getEmail() + "'");

					if(customer.getPassword() != null)
						condition.append(" c.password = '" + customer.getPassword() + "'");
					else
						condition.append(" AND c.password = '" + customer.getPassword() + "'");

					if(customer.getCreatedDate() != null)
						condition.append(" c.createdDate = '" + customer.getCreatedDate() + "'");
					else
						condition.append(" AND c.createdDate = '" + customer.getCreatedDate() + "'");

					if(customer.getUserStatus() != null)
						condition.append(" c.userStatus = '" + customer.getUserStatus() + "'");
					else
						condition.append(" AND c.userStatus = '" + customer.getUserStatus() + "'");

					if(condition.length() > 0) {
						query.append(condition);
					}
				}
			}

			try {

				stmt = conn.createStatement();

				if(stmt.execute(query.toString())) {
					ResultSet r = stmt.getResultSet();
					return new RentalList(r, objectLayer);
				}
			}
			catch(Exception e) {
				throw new RARException("CustomerManager.restoreRentalCustomer: Could not restore persistent Rental objects; Root cause: " + e);
			}

			throw new RARException("CustomerManager.restoreRentalCustomer: Could not restore persistent Rental objects");
		}

	public void delete(Customer customer)
		throws RARException
		{
			String               deleteCSql = "delete from Customer where id = ?";
			PreparedStatement    stmt = null;
			int                  inscnt;

			if(!customer.isPersistent())
				return;

			try {
				stmt = (PreparedStatement) conn.prepareStatement(deleteCSql);
				stmt.setLong(1, customer.getId());
				inscnt = stmt.executeUpdate();
				if(inscnt == 1) {
					return;
				}
				else
					throw new RARException("CustomerManager.delete: failed to delete a Customer");
			}
			catch(SQLException e) {
				e.printStackTrace();
				throw new RARException("CustomerManager.delete: failed to delete a Customer: " + e);        }
		}
}


