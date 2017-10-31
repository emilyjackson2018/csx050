package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.entity.RentARideParams;
import edu.uga.cs.rentaride.entity.UserStatus;
import edu.uga.cs.rentaride.entity.impl.RentARideParamsImpl;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class RentARideParamsManager {
	private ObjectLayer objectLayer = null;
    private Connection  conn = null;
	
    public RentARideParamsManager(Connection conn, ObjectLayer objectLayer) {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void save(RentARideParams rentARideParams) throws RARException {
    	String               insertSQL = "insert into rentARideParams (membership_price, late_fee) values (?, ?)";
        String               updateSQL = "update rentARideParams set membership_price = ?, late_fee = ? where id = ? ";
        PreparedStatement    stmt = null;
        int                  inscnt;
        long                 id;
        
        try {
        	
	        if (!rentARideParams.isPersistent())
	            stmt = (PreparedStatement) conn.prepareStatement(insertSQL);
	        else
	            stmt = (PreparedStatement) conn.prepareStatement(updateSQL);
	        
	        //Membership Price
            if (rentARideParams.getMembershipPrice() != -1)
                stmt.setInt(1, rentARideParams.getMembershipPrice());
            else
            	 throw new RARException("RentARideParamsManager.save: can't save a rentARideParams: Membership price is undefined");
	        
            //late fee
            if (rentARideParams.getLateFee() != -1)
                stmt.setInt(2, rentARideParams.getLateFee());
            else
            	 throw new RARException("RentARideParamsManager.save: can't save a rentARideParams: Late fee is undefined");
	        
            inscnt = stmt.executeUpdate();
            
            if (!rentARideParams.isPersistent()) {
                if (inscnt >= 1) {
                    String sql = "SELECT LAST_INSERT_ID()";
                    if(stmt.execute(sql)) { // statement returned a result

                        //Retrieve the result
                        ResultSet rs = stmt.getResultSet();

                        while(rs.next()) {

            				//Retrieve the last insert AUTO_INCREMENT value
                            id = rs.getLong(1);
                            if(id > 0)
                            	rentARideParams.setId(id); // set this rentARideParams's db id (proxy object)
                        }
                    }
                }
                else
                    throw new RARException("RentARideParamsManager.save: failed to save an Hourly Price");
            }
            else {
                if( inscnt < 1 )
                    throw new RARException("RentARideParamsManager.save: failed to save an Hourly Price"); 
            }
        }//try 
        catch (SQLException e)  {
        	e.printStackTrace();
            throw new RARException("RentARideParamsManager.save: failed to save an hourly price: " + e);
        }
        throw new RARException( "RentARideParamsManager.restore: Could not restore persistent hourly price object" );
        
    }
    
    //Restore
    public List<RentARideParams> restore(RentARideParams rentARideParams) throws RARException {
    	
    	String       selectASQL = "SELECT a.max_hours, a.price " +
                " from hourly_price a  ";
	Statement    stmt = null;
	StringBuffer query = new StringBuffer(100);
	StringBuffer condition = new StringBuffer(100);
	List<RentARideParams> rentARideParamss = new ArrayList<RentARideParams>();
	
	condition.setLength(0);
	
	query.append(selectASQL);
	
	if (rentARideParams != null) {
		if (rentARideParams.getId() >= 0) {
			query.append(" WHERE id = " + rentARideParams.getId());
		} else {
			if (rentARideParams.getMembershipPrice() != -1)
				condition.append(" AND membership_price = '" + rentARideParams.getMembershipPrice() + "'");
			
			if (rentARideParams.getLateFee() != -1)
				condition.append(" AND late_fee = '" + rentARideParams.getLateFee() + "'");
		} //else
	}//if
	
	try {
		
		stmt = conn.createStatement();
		
		if (stmt.execute(query.toString())) {
			int		membershipPrice;
			int		lateFee;
			RentARideParams rentARideParamsProxy = null;
		    
			ResultSet rs = stmt.getResultSet();
			
			while (rs.next()) {
				membershipPrice 	= rs.getInt(1);
				lateFee				= rs.getInt(2);
				
				rentARideParamsProxy = objectLayer.createRentARideParams();
				rentARideParamsProxy.setMembershipPrice(membershipPrice);
				rentARideParamsProxy.setLateFee(lateFee);
				
				rentARideParamss.add(rentARideParamsProxy);
			}
			return rentARideParamss;
		}
	} catch (SQLException e) {
		throw new RARException("AdministratorManager.restore: Could not restore persistent objects; Root cause: " + e );
	}
	throw new RARException( "AdministratorManager.restore: Could not restore persistent Administrator object" );
    	
    }//restored
    
    //Delete method
    public void delete(RentARideParamsImpl rentARideParams) throws RARException {
    	String               deleteCommentSQL = "delete from rentARideParams where id = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        if(!rentARideParams.isPersistent()) return; //Jump out if not persistent, nothing to be deleted
        
        try {
            
            stmt = (PreparedStatement) conn.prepareStatement( deleteCommentSQL );
            stmt.setLong( 1, rentARideParams.getId() );
            inscnt = stmt.executeUpdate();
            
            if (inscnt == 1) {
            	return;
            } else {
            	throw new RARException( "RentARideParamsManager.delete: failed to delete this hourly price" );
            }
            
        }//try 
        catch( SQLException e ) {
            throw new RARException( "RentARideParamsManager.delete: failed to delete this hourly price: " + e.getMessage() );
        }//catch
    }
}
