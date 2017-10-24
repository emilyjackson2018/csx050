package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.impl.VehicleImpl;
import edu.uga.cs.rentaride.entity.impl.ReservationImpl;
import edu.uga.cs.rentaride.entity.impl.RentalLocationImpl;
import edu.uga.cs.rentaride.object.impl.ObjectLayerImpl;
import edu.uga.cs.rentaride.entity.impl.CommentImpl;
import edu.uga.cs.rentaride.entity.impl.CustomerImpl;
import edu.uga.cs.rentaride.entity.impl.RentalImpl;


public class Comment
    implements Iterator<CommentImpl> {
    private ResultSet   rs = null;
    private ObjectLayerImpl objectLayer = null;
    private boolean     more = false;

    public Comment(ResultSet rs, ObjectLayerImpl objectLayer) throws RARException {
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
    	CommentImpl comment;
    	Date commentDate;
    	String customerID;
    	int rentalNo;
    	String commentText;
    	RentalImpl rental = null;
    	CustomerImpl customer =null;
    	
        
        
        if(more) {

            try {
            	commentDate = rs.getDate("commentDate");
            	customerID = rs.getString("customer");
            	rentalNo = rs.getInt("rental");
            	commentText = rs.getString("comment");
            	
                more = rs.next();
            }
            catch(Exception e) {
                throw new NoSuchElementException("Comment: No next Comment object; root cause: " + e);
            }
            
            
            rental = objectLayer.findRental(modelRental)(rentalNo);
            customer = objectLayer.findCustomer(customerID);
            
            try {
                comment = objectLayer.createComment(commentText,rental,customer);
                
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


