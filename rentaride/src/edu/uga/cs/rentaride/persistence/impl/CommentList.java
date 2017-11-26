package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.impl.*;
import edu.uga.cs.rentaride.object.impl.*;
import edu.uga.cs.rentaride.entity.*;


public class CommentList
    implements Iterator<CommentImpl> {
    private ResultSet   rs = null;
    private ObjectImpl objectLayer = null;
    private boolean     more = false;

    public CommentList(ResultSet rs, ObjectImpl objectLayer) throws RARException {
        this.rs = rs;
        this.objectLayer = objectLayer;
        try {
            more = rs.next();
        }
        catch(Exception e) {
            throw new RARException("Comment: Cannot create an iterator; root cause: " + e);
        }
    }

    public boolean hasNext() { 
        return more; 
    }

    public CommentImpl next() {
    	CommentImpl comment = null;
    	Date commentDate;
    	Customer customerID = null;
    	//int rentalNo;
    	String commentText;
    	Rental rental = null;
    	Customer customer = null;
    	
        
        
        if(more) {

            try {
            	commentDate = rs.getDate("commentDate");
            	//customerID = rs.getString("customer");
            	//rentalNo = rs.getInt("rental");
            	commentText = rs.getString("comment");
            	
                more = rs.next();
            }
            catch(Exception e) {
                throw new NoSuchElementException("Comment: No next Comment object; root cause: " + e);
            }
            
            
           Rental modelRental = null;
		try {
			rental = (Rental) objectLayer.findRental(modelRental);
		} catch (RARException e) {
			throw new NoSuchElementException("Comment: No next Rental object; root cause: " + e);
		}
           try {
			customer = (Customer) objectLayer.findCustomer(customerID);
		} catch (RARException e) {
			throw new NoSuchElementException("Comment: No next Customer object; root cause: " + e);
		}
            
            try {
                comment = objectLayer.createComment(commentText,commentDate,rental);
                
            }
            catch(RARException re) {
                re.printStackTrace();
                System.out.println(re);
            }

            return comment;
        }
        else {
            throw new NoSuchElementException("Comment: No next Comment object");
        }
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

}


