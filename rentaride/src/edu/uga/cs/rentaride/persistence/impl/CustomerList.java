package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Comment;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class CustomerList
implements Iterator<Customer>
{
	private ResultSet   rs = null;
	private ObjectLayer objectLayer = null;
	private boolean     more = false;

	public CustomerList(ResultSet rs, ObjectLayer objectLayer)
		throws RARException
		{
			this.rs = rs;
			this.objectLayer = objectLayer;
			try {
				more = rs.next();
			}
			catch(Exception e) {
				throw new RARException("Customer: Cannot create an iterator; root cause: " + e);
			}
		}

	public boolean hasNext()
	{
		return more;
	}

	public Customer next()
	{
		long id;
    String fName;
    String lName;
    String uName;
    String password;
    String email;
    String address;
    Date createDate;
		Date memberUntil;
		String licState;
		String licNumber;
		String ccNumber;
		Date ccExpiration;
		Customer customer = null;
		List<Reservation> reservationList = null;
		List<Comment> commentList = null; 
		List<Rental> rentalList = null;


		if(more) {

			try {
				id = rs.getLong("id");
        fName = rs.getString("firstName");
        lName = rs.getString("lastName");
        uName = rs.getString("userName");
        password = rs.getString("password");
        email = rs.getString("email");
        address = rs.getString("address");
        createDate = rs.getDate("createDate");
				memberUntil = rs.getDate("memberUntil");
				licState = rs.getString("licState");
				licNumber = rs.getString("licNumber");
				ccNumber = rs.getString("ccNumber");
				ccExpiration = rs.getDate("ccExpiration");
				//reservationList = rs.getString("reservationList");
			
				more = rs.next();
			}
			catch(Exception e) {
				throw new NoSuchElementException("Customer: No next Customer object; root cause: " + e);
			}

			try {
				customer = objectLayer.createCustomer(fName, lName, uName, password, email, address, createDate, memberUntil, licState, licNumber, ccNumber, ccExpiration);
			} catch (RARException e) {
				//throw new RARException("Customer: Cannot create a; root cause: " + e);
			}
			customer.setId(id);

			return customer;
		}
		else {
			throw new NoSuchElementException("Customer: No next Customer object");
		}
	}

	public void remove()
	{
		throw new UnsupportedOperationException();
	}

}

