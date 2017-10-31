package edu.uga.cs.rentaride.persistence.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.impl.HourlyPriceImpl;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class HourlyPriceManager {
	private ObjectLayer objectLayer = null;
    private Connection  conn = null;
	
    public HourlyPriceManager(java.sql.Connection conn, ObjectLayer objectLayer) {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void save(HourlyPrice hourlyPrice) throws RARException {
    	String               insertSQL = "insert into hourlyPrice (max_hours, price, type_id values (?, ?, ?)";
        String               updateSQL = "update hourlyPrice set max_hours = ?, price = ?, type_id = ? where id = ? ";
        PreparedStatement    stmt = null;
        int                  inscnt;
        long                 id;
        
        try {
        	
	        if (!hourlyPrice.isPersistent())
	            stmt = (PreparedStatement) conn.prepareStatement(insertSQL);
	        else
	            stmt = (PreparedStatement) conn.prepareStatement(updateSQL);
	        
	        //MaxHours
	        if (hourlyPrice.getMaxHours() != -1 || hourlyPrice.getMaxHours() > -1) { //or not greater than minHours, getMinHours?
	        	stmt.setInt(1, hourlyPrice.getMaxHours());
	        } else 
	        	stmt.setNull(1, java.sql.Types.VARCHAR); //Throw error instead? Unsure
	        
	        //Price
	        if (hourlyPrice.getPrice() != -1 || hourlyPrice.getPrice()>-1) {
	        	stmt.setInt(2, hourlyPrice.getPrice());
	        } else 
	        	stmt.setNull(2, java.sql.Types.VARCHAR); //Throw error instead? Unsure
	        
	        //Type_id
	        if (hourlyPrice.getVehicleType() != null) {
	        	stmt.setLong(3, hourlyPrice.getVehicleType().getId());
	        } else 
	        	stmt.setNull(3, java.sql.Types.INTEGER); //Throw error instead? Unsure
	        
            inscnt = stmt.executeUpdate();
            
            if (!hourlyPrice.isPersistent()) {
                if (inscnt >= 1) {
                    String sql = "SELECT LAST_INSERT_ID()";
                    if(stmt.execute(sql)) { // statement returned a result

                        //Retrieve the result
                        ResultSet rs = stmt.getResultSet();

                        while(rs.next()) {

            				//Retrieve the last insert AUTO_INCREMENT value
                            id = rs.getLong(1);
                            if(id > 0)
                            	hourlyPrice.setId(id); // set this hourlyPrice's db id (proxy object)
                        }
                    }
                }
                else
                    throw new RARException("HourlyPriceManager.save: failed to save an Hourly Price");
            }
            else {
                if( inscnt < 1 )
                    throw new RARException("HourlyPriceManager.save: failed to save an Hourly Price"); 
            }
        }//try 
        catch (SQLException e)  {
        	e.printStackTrace();
            throw new RARException("HourlyPriceManager.save: failed to save an hourly price: " + e);
        }
        throw new RARException( "HourlyPriceManager.restore: Could not restore persistent hourly price object" );
        
    }
    
    //Restore
    public List<HourlyPrice> restore(HourlyPrice hourlyPrice) throws RARException {
    	
    	String       selectASQL = "SELECT a.max_hours, a.price " +
                " from hourly_price a  ";
	Statement    stmt = null;
	StringBuffer query = new StringBuffer(100);
	StringBuffer condition = new StringBuffer(100);
	List<HourlyPrice> hourlyPrices = new ArrayList<HourlyPrice>();
	
	condition.setLength(0);
	
	query.append(selectASQL);
	
	if (hourlyPrice != null) {
		if (hourlyPrice.getId() >= 0) {
			query.append(" WHERE id = " + hourlyPrice.getId());
		} else {
			if (hourlyPrice.getMaxHours() != -1)
				condition.append(" AND max_hours = '" + hourlyPrice.getMaxHours() + "'");
			
			if (hourlyPrice.getPrice() != -1)
				condition.append(" AND price = '" + hourlyPrice.getPrice() + "'");
		} //else
	}//if
	
	try {
		
		stmt = conn.createStatement();
		
		if (stmt.execute(query.toString())) {
			int		max_hours;
			int		price;
			HourlyPrice hourlyPriceProxy = null;
		    
			ResultSet rs = stmt.getResultSet();
			
			while (rs.next()) {
				max_hours 	= rs.getInt(1);
				price		= rs.getInt(2);
				
				hourlyPriceProxy = objectLayer.createHourlyPrice();
				hourlyPriceProxy.setMaxHours(max_hours);
				hourlyPriceProxy.setPrice(price);
				
				hourlyPrices.add(hourlyPriceProxy);
			}
			return hourlyPrices;
		}
	} catch (SQLException e) {
		throw new RARException("AdministratorManager.restore: Could not restore persistent objects; Root cause: " + e );
	}
	throw new RARException( "AdministratorManager.restore: Could not restore persistent Administrator object" );
    	
    }//restored
    
    //Delete method
    public void delete(HourlyPriceImpl hourlyPrice) throws RARException {
    	String               deleteCommentSQL = "delete from hourlyPrice where id = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        if(!hourlyPrice.isPersistent()) return; //Jump out if not persistent, nothing to be deleted
        
        try {
            
            stmt = (PreparedStatement) conn.prepareStatement( deleteCommentSQL );
            stmt.setLong( 1, hourlyPrice.getId() );
            inscnt = stmt.executeUpdate();
            
            if (inscnt == 1) {
            	return;
            } else {
            	throw new RARException( "HourlyPriceManager.delete: failed to delete this hourly price" );
            }
            
        }//try 
        catch( SQLException e ) {
            throw new RARException( "HourlyPriceManager.delete: failed to delete this hourly price: " + e.getMessage() );
        }//catch
    }
}