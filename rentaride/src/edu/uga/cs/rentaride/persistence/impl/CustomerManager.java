package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Connection;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.UserStatus;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class CustomerManager {

	private ObjectLayer objectLayer = null;
    private Connection  conn = null;
	
    public CustomerManager(Connection conn, ObjectLayer objectLayer) {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
	
    public void save(Customer customer) throws RARException {
    	
    	String               insertASql = "insert into customer (first_name, last_name, username, password, email, address, create_date, "
    									+ "member_until, license_state, license_num, cc_number, cc_expiration, status) "
    									+ "values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String               updateASql = "update customer set first_name = ?, last_name = ?, username = ?, password = ?, email = ?, "
        								+ "address = ?, create_date = ?, member_until =?, license_state = ?, license_num = ?, "
        								+ "cc_number = ?m cc_expiration = ?, status = ? where id = ? ";
        PreparedStatement    stmt = null;
        int                  inscnt;
        long                 id;

        try {
            if (!customer.isPersistent())
                stmt = (PreparedStatement) conn.prepareStatement(insertASql);
            else
                stmt = (PreparedStatement) conn.prepareStatement(updateASql);
            
            //First name
            if (customer.getFirstName() != null)
                stmt.setString(1, customer.getFirstName());
            else
            	 throw new RARException("CustomerManager.save: can't save an Customer: First Name is undefined");
                 //stmt.setNull( 1, java.sql.Types.VARCHAR ); <--- How do we know not to do this instead of throwing an error?
            
            //Last name
            if (customer.getLastName() != null)
                stmt.setString(2, customer.getLastName());
            else
            	 throw new RARException("CustomerManager.save: can't save an Customer: Last Name is undefined");
            
            //Username
            if (customer.getUserName() != null)
                stmt.setString(3, customer.getUserName());
            else
            	 throw new RARException("CustomerManager.save: can't save an Customer: Username is undefined");
            
            //Password
            if (customer.getPassword() != null)
                stmt.setString(4, customer.getPassword());
            else
            	 throw new RARException("CustomerManager.save: can't save an Customer: Password is undefined");
            
            //Email
            if (customer.getEmail() != null)
                stmt.setString(5, customer.getEmail());
            else
            	 throw new RARException("CustomerManager.save: can't save an Customer: Email is undefined");
            
            //Address
            if (customer.getAddress() != null)
                stmt.setString(6, customer.getAddress());
            else
            	 throw new RARException("CustomerManager.save: can't save an Customer: Address is undefined");
            
            //Created Date
            if (customer.getCreatedDate() != null)
            	stmt.setDate(7, (java.sql.Date) customer.getCreatedDate());
            else
                throw new RARException("Customer.save: can't save a Customer: Created Date is undefined");
            
            //Member until
            if (customer.getMemberUntil() != null)
            	stmt.setDate(8, (java.sql.Date) customer.getMemberUntil());
            else
                throw new RARException("Customer.save: can't save a Customer: Member Until is undefined");
            
            //License State
            if (customer.getLicenseState() != null)
            	stmt.setString(9, customer.getLicenseState());
            else
                throw new RARException("Customer.save: can't save a Customer: License State is undefined");
            
            //License State
            if (customer.getLicenseNumber() != null)
            	stmt.setString(10, customer.getLicenseNumber());
            else
                throw new RARException("Customer.save: can't save a Customer: License Number is undefined");
            
            //Credit Card Number
            if (customer.getCreditCardNumber() != null)
            	stmt.setString(11, customer.getCreditCardNumber());
            else
                throw new RARException("Customer.save: can't save a Customer: Credit Card Number is undefined");
            
            //Credit Card Expiration Date
            if (customer.getCreditCardExpiration() != null)
            	stmt.setDate(12, (java.sql.Date) customer.getCreditCardExpiration());
            else
                throw new RARException("Customer.save: can't save a Customer: Credit Care Expiration is undefined");
            
            //Status
            if (customer.getUserStatus() != null)
                stmt.setString( 13, customer.getUserStatus().toString() );
            else
            	 throw new RARException("Customer.save: Can't save an Customer: Status is not set or not persistent");
            
            //Only for update SQL statement
            if (customer.isPersistent())
                stmt.setLong(14, customer.getId());

            inscnt = stmt.executeUpdate();
            
            //Executing SQL query
            if (!customer.isPersistent()) {
            	if (inscnt >= 1) { //If the query returns a result
            		String sql = "SELECT LAST_INSERT_ID()";
            		if (stmt.execute(sql)) { 
            			
            			//Retrieve the ResultSet
            			ResultSet rs = stmt.getResultSet();
            			
            			while (rs.next()) {
            				
            				//Retrieve the last insert AUTO_INCREMENT value
                            id = rs.getLong(1);
                            if (id != -1) //id not "null"/-1
                                customer.setId(id); //Set the id
            			}//while
            		}//if
            	}//if
	            else
	            	throw new RARException("CustomerManager.save: failed to save an customer");
	        }//if 
	        else {
	        	if (inscnt < 1)
	        		throw new RARException("CustomerManager.save: failed to save an customer");
	        }
        }
        catch (Exception e) {
        	throw new RARException("CustomerManager.save: failed to save a Customer");
        }
    	
	}//Save
	
    //Restore
	public List<Customer> restore(Customer customer) throws RARException{
		String       selectASQL = "SELECT c.first_name, c.last_name, c.username, c.password, c.email, c.address, c.create_date, c.member_until,"
				+ " c.license_state, c.license_num, c.cc_number, c.cc_expiration, c.status " +
                " from customer c ";
		Statement    stmt = null;
		StringBuffer query = new StringBuffer(100);
		StringBuffer condition = new StringBuffer(100);
		List<Customer> customers = new ArrayList<Customer>();
		
		condition.setLength(0);
		
		query.append(selectASQL);
		
		if (customer != null) {
			if (customer.getId() >= 0) {
				query.append(" WHERE id = " + customer.getId());
			} else {
				if (customer.getFirstName() != null)
					condition.append(" AND first_name = '" + customer.getFirstName() + "'");
				
				if (customer.getLastName() != null)
					condition.append(" AND last_name = '" + customer.getLastName() + "'");
				
				if (customer.getUserName() != null)
					condition.append(" AND username = '" + customer.getUserName() + "'");
				
				if (customer.getPassword() != null)
					condition.append(" AND password = '" + customer.getPassword() + "'");
				
				if (customer.getEmail() != null)
					condition.append(" AND email = '" + customer.getEmail() + "'");
				
				if (customer.getAddress() != null)
					condition.append(" AND address = '" + customer.getAddress() + "'");
	
				if (customer.getCreatedDate() != null)
					condition.append(" AND create_date = '" + customer.getCreatedDate() + "'");
				
				if (customer.getMemberUntil() != null)
					condition.append(" AND member_until = '" + customer.getMemberUntil() + "'");
				
				if (customer.getLicenseState() != null)
					condition.append(" AND license_state = '" + customer.getLicenseState() + "'");
				
				if (customer.getLicenseNumber() != null)
					condition.append(" AND license_number = '" + customer.getLicenseNumber() + "'");
				
				if (customer.getCreditCardNumber() != null)
					condition.append(" AND cc_number = '" + customer.getCreditCardNumber() + "'");
				
				if (customer.getCreditCardExpiration() != null)
					condition.append(" AND cc_expiration = '" + customer.getCreditCardExpiration() + "'");
				
				if (customer.getUserStatus() != null)
					condition.append(" AND status = '" + customer.getUserStatus() + "'");
			} //else
		}//if
		
		try {
			
			stmt = conn.createStatement();
			
			if (stmt.execute(query.toString())) {
				String		firstName;
			    String		lastName;
			    String		userName;
			    String		password;
			    String		email;
			    String		address;
			    Date		createDate;
			    Date		memberUntil;
			    String		licenseState;
			    String		licenseNum;
			    String		ccNum;
			    Date		ccExpiration;
				Customer customerProxy = null;
			    
				ResultSet rs = stmt.getResultSet();
				
				while (rs.next()) {
					firstName 		= rs.getString(1);
					lastName 		= rs.getString(2);
					userName 		= rs.getString(3);
					password 		= rs.getString(4);
					email			= rs.getString(5);
					address 		= rs.getString(6);
					createDate 		= rs.getDate(7);
					memberUntil 	= rs.getDate(8);
					licenseState	= rs.getString(9);
					licenseNum	 	= rs.getString(10);
					ccNum 			= rs.getString(11);
					ccExpiration 	= rs.getDate(12);
					
					customerProxy = objectLayer.createCustomer();
					customerProxy.setFirstName(firstName);
					customerProxy.setLastName(lastName);
					customerProxy.setUserName(userName);
					customerProxy.setPassword(password);
					customerProxy.setEmail(email);
					customerProxy.setAddress(address);
					customerProxy.setCreateDate(createDate);
					customerProxy.setMemberUntil(memberUntil);
					customerProxy.setLicenseState(licenseState);
					customerProxy.setLicenseNumber(licenseNum);
					customerProxy.setCreditCardNumber(ccNum);
					customerProxy.setCreditCardExpiration(ccExpiration);
					customerProxy.setUserStatus(UserStatus.ACTIVE);
					
					customers.add(customerProxy);
				}
				return customers;
			}
		} catch (SQLException e) {
			throw new RARException("CustomerManager.restore: Could not restore persistent objects; Root cause: " + e );
		}
		throw new RARException( "CustomerManager.restore: Could not restore persistent Customer object" );
	
	}//Restore
	
	//Delete
	public void delete(Customer customer) throws RARException {
		String               deleteCommentSQL = "delete from customer where id = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        if(!customer.isPersistent()) return; //Jump out if not persistent, nothing to be deleted
        
        try {
            
            stmt = (PreparedStatement) conn.prepareStatement( deleteCommentSQL );
            stmt.setLong( 1, customer.getId() );
            inscnt = stmt.executeUpdate();
            
            if (inscnt == 1) {
            	return;
            } else {
                throw new RARException( "CustomerManager.delete: failed to delete this customer" );
            }
            
        }//try 
        catch( SQLException e ) {
            throw new RARException( "CustomerManager.delete: failed to delete this customer: " + e.getMessage() );
        }//catch
	}//Delete

	//Restore Customer Reservation returning Reservation List with customer parameter
	public List<Reservation> restoreCustomerReservation(Customer customer) throws RARException{
		
    	String      		selectASQL = "SELECT r.id, r.customer_id, r.rental_location, r.vehicle_type_id, r.pickup_time, r.length, r.cancelled" 
    									+ " from Reservation r WHERE";
        Statement 			stmt = null;
        StringBuffer 		query = new StringBuffer(100);
        StringBuffer 		condition = new StringBuffer(100);
        List<Reservation> 	reservations = new ArrayList<Reservation>();
    	
        condition.setLength(0);
        
        query.append(selectASQL);
        
        if( customer != null) {
        	if(customer.getId() >= 0)
        		query.append(" AND id = " + customer.getId());
        	else{
	            if(customer.getFirstName()!=null)
	                condition.append(" AND first_name = '" + customer.getFirstName() + "'");
	            if(customer.getLastName()!=null)
	                condition.append(" AND last_name = '" + customer.getLastName() + "'");
	            if(customer.getUserName()!=null)
	                condition.append(" AND username = '" + customer.getUserName() + "'");
	            if(customer.getPassword()!=null)
	                condition.append(" AND password = '" + customer.getPassword() + "'");
	            if(customer.getEmail()!=null)
	                condition.append(" AND email = '" + customer.getEmail() + "'");
	            if(customer.getAddress()!= null)
	            	condition.append(" AND address = '" + customer.getAddress() + "'");
	            if(customer.getCreatedDate()!= null)
	            	condition.append(" AND create_date = '" + customer.getCreatedDate() + "'");
	            if(customer.getMemberUntil()!= null)
	            	condition.append(" AND member_until = '" + customer.getMemberUntil() + "'"); 
	            if(customer.getLicenseState()!= null)
	            	condition.append(" AND license_state = '" + customer.getLicenseState() + "'");
	            if(customer.getLicenseNumber()!= null)
	            	condition.append(" AND license_number = '" + customer.getLicenseNumber() + "'");
	            if(customer.getCreditCardNumber()!= null)
	            	condition.append(" AND cc_number = '" + customer.getCreditCardNumber() + "'");
	            if(customer.getCreditCardExpiration()!= null)
	            	condition.append(" AND cc_expiration = '" + customer.getCreditCardExpiration() + "'");
	            if(customer.getUserStatus()!= null)
	            	condition.append(" AND status = '" + customer.getUserStatus() + "'"); 
	        }	
        
        }
        
        
        try{
            
            stmt = conn.createStatement();
            
            if(stmt.execute(query.toString())){
            	
                long    			id;
                Date			  	pickup_time;
                int					length;
                boolean				cancelled;
                
                Reservation ReservationProxy = null;
                
                ResultSet rs = stmt.getResultSet();
                
                while(rs.next()){
                    id = 				rs.getLong(1);
                    pickup_time = 		rs.getDate(2);
                    length = 			rs.getInt(3);
                    cancelled = 		rs.getBoolean(4);
                    
                    ReservationProxy.setId(id);
                    ReservationProxy.setPickupTime(pickup_time);
                    ReservationProxy.setLength(length);
            
                    ReservationProxy = objectLayer.createReservation();
                    
                    
                    reservations.add(ReservationProxy);
                }
                return reservations;
            }
            
        }
        
        catch(Exception e){
            throw new RARException("ReservationManager.restore: Could not restore persistent objects: Root cause: " + e);
        }
     throw new RARException("ReservationMangaer.restore: Could not restore persistent Reservation object");
     
    /*else
    	throw new RARException("Customermanager.restore: Could not restore persistent Reservation object");*/

	}//restore

	
	
}
