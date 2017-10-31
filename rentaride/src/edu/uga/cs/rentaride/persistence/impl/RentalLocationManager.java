package edu.uga.cs.rentaride.persistence.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class RentalLocationManager {
	private ObjectLayer objectLayer = null;
    private Connection  conn = null;
	
    public RentalLocationManager(java.sql.Connection conn, ObjectLayer objectLayer) {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void save(RentalLocation RentalLocation) throws RARException {
    	String               insertSQL = "insert into RentalLocation (name, address, capacity values (?, ?, ?)";
        String               updateSQL = "update RentalLocation set name = ?, address = ?, capacity = ? where id = ? ";
        PreparedStatement    stmt = null;
        int                  inscnt;
        long                 id;
        
        try {
	        if( !RentalLocation.isPersistent() )
	            stmt = (PreparedStatement) conn.prepareStatement(insertSQL);
	        else
	            stmt = (PreparedStatement) conn.prepareStatement(updateSQL);
	        
	        //Name
	        if (RentalLocation.getName() != null) { //or non-unique
	        	stmt.setString(1, RentalLocation.getName());
	        } else 
	        	stmt.setNull(1, java.sql.Types.VARCHAR); //Throw error instead? Unsure
	        
	        //Address
	        if (RentalLocation.getAddress() != null) {
	        	stmt.setString(2, RentalLocation.getAddress());
	        } else 
	        	stmt.setNull(2, java.sql.Types.VARCHAR); //Throw error instead? Unsure
	        
	        //Capacity
	        if (RentalLocation.getCapacity() != -1) {
	        	stmt.setInt(3, RentalLocation.getCapacity());
	        } else 
	        	stmt.setNull(3, java.sql.Types.INTEGER); //Throw error instead? Unsure
	        
	        
            inscnt = stmt.executeUpdate();
            
            if (!RentalLocation.isPersistent()) {
                if (inscnt >= 1) {
                    String sql = "SELECT LAST_INSERT_ID()";
                    if(stmt.execute(sql)) { // statement returned a result

                        //Retrieve the result
                        ResultSet rs = stmt.getResultSet();

                        while(rs.next()) {

            				//Retrieve the last insert AUTO_INCREMENT value
                            id = rs.getLong(1);
                            if(id > 0)
                            	RentalLocation.setId(id); // set this RentalLocation's db id (proxy object)
                        }
                    }
                }
                else
                    throw new RARException("RentalLocationManager.save: failed to save a rental location");
            }
            else {
                if( inscnt < 1 )
                    throw new RARException("RentalLocationManager.save: failed to save a rental location"); 
            }
        }//try 
        catch (SQLException e)  {
        	e.printStackTrace();
            throw new RARException("RentalLocationManager.save: failed to save an rental location: " + e);
        }
        throw new RARException( "RentalLocationManager.restore: Could not restore persistent rental location object" );
        
    }
    
    //Needs to be completed
    public List<RentalLocation> restore(RentalLocation rentalLocation) throws RARException {
    	
    	String       selectASQL = "SELECT r.id, r.name, r.address, r.capacity " + " from rentalLocation r WHERE";
        Statement 	 stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);
        List<RentalLocation> rentalLocations = new ArrayList<RentalLocation>();
    	
        condition.setLength(0);
        
        query.append(selectASQL);
        
        if (rentalLocation != null) {
		    if(rentalLocation.getId() >= 0) {
		        query.append(" AND id = " + rentalLocation.getId());
		    } else {
		        if(rentalLocation.getId()!=-1)
		            condition.append(" AND id = '" + rentalLocation.getId() + "'");
		        if(rentalLocation.getName()!=null)
		            condition.append(" AND name = '" + rentalLocation.getName() + "'");
		        if(rentalLocation.getAddress()!=null)
		            condition.append(" AND address = '" + rentalLocation.getAddress() + "'");
		        if(rentalLocation.getCapacity()!=-1)
		            condition.append(" AND capacity = '" + rentalLocation.getCapacity() + "'");
		    } //else
        }//if
        
        try {
            
            stmt = conn.createStatement();
            
            if(stmt.execute(query.toString())){
                long    	id;
                String		name;
                String		address;
                int			capacity;
                
                RentalLocation rentalLocationProxy = null;
                
                ResultSet rs = stmt.getResultSet();
                
                while(rs.next()){
                    id = rs.getLong(1);
                    name = rs.getString(2);
                    address = rs.getString(3);
                    capacity = rs.getInt(4);
                    //vehicle = rs.getString(5);
                    
                    rentalLocationProxy = objectLayer.createRentalLocation();
                    rentalLocationProxy.setId(id);
                    rentalLocationProxy.setName(name);
                    rentalLocationProxy.setAddress(address);
                    rentalLocationProxy.setCapacity(capacity);
                    
                    rentalLocations.add(rentalLocationProxy);
                    
                }
                return rentalLocations;
            }
        }
        
        catch(SQLException e){
            throw new RARException("RentalManager.restore: COuld not restore persistent objects: Root cause: " + e);
        }
    throw new RARException("RentalManger.restore: Could not restore persistent Rental object");
    	
    }
    
    public void delete(RentalLocation RentalLocation) throws RARException {
    	String               deleteCommentSQL = "delete from RentalLocation where id = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        if(!RentalLocation.isPersistent()) return; //Jump out if not persistent, nothing to be deleted
        
        try {
            
            stmt = (PreparedStatement) conn.prepareStatement( deleteCommentSQL );
            stmt.setLong( 1, RentalLocation.getId() );
            inscnt = stmt.executeUpdate();
            
            if (inscnt == 1) {
            	return;
            } else {
            	throw new RARException( "RentalLocationManager.delete: failed to delete this rental location" );
            }
            
        }//try 
        catch( SQLException e ) {
            throw new RARException( "RentalLocationManager.delete: failed to delete this rental location: " + e.getMessage() );
        }//catch
    }
}