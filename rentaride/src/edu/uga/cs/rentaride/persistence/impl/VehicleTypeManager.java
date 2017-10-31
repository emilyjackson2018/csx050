package edu.uga.cs.rentaride.persistence.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class VehicleTypeManager {
	private ObjectLayer objectLayer = null;
    private Connection  conn = null;
	
    public VehicleTypeManager(java.sql.Connection conn, ObjectLayer objectLayer) {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void save(VehicleType VehicleType) throws RARException {
    	String               insertSQL = "insert into VehicleType (name, values (?)";
        String               updateSQL = "update VehicleType set name = ?, where id = ? ";
        PreparedStatement    stmt = null;
        int                  inscnt;
        long                 id;
        
        try {
	        if( !VehicleType.isPersistent() )
	            stmt = (PreparedStatement) conn.prepareStatement(insertSQL);
	        else
	            stmt = (PreparedStatement) conn.prepareStatement(updateSQL);
	        
	        //Name
	        if (VehicleType.getName() != null) { 
	        	stmt.setString(1, VehicleType.getName());
	        } else 
	        	stmt.setNull(1, java.sql.Types.VARCHAR); //Throw error instead? Unsure
	        
//	        //HourlyPrice
//	        if (VehicleType.getHourlyPrice() != null || VehicleType.getHourlyPrice()>-1) {
//	        	stmt.setInt(2, VehicleType.getHourlyPrice());
//	        } else 
//	        	stmt.setNull(2, java.sql.Types.VARCHAR); //Throw error instead? Unsure
//	        
//	        //Vehicles
//	        if (VehicleType.getVehicles() != null) {
//	        	stmt.setLong(3, VehicleType.getVehicles());
//	        } else 
//	        	stmt.setNull(3, java.sql.Types.INTEGER); //Throw error instead? Unsure
//	        
//	        //Reservations
//	        if (VehicleType.getReservations() != null) {
//	        	stmt.setLong(4, VehicleType.getReservations());
//	        } else 
//	        	stmt.setNull(4, java.sql.Types.INTEGER); //Throw error instead? Unsure
	        
            inscnt = stmt.executeUpdate();
            
            if (!VehicleType.isPersistent()) {
                if (inscnt >= 1) {
                    String sql = "SELECT LAST_INSERT_ID()";
                    if(stmt.execute(sql)) { // statement returned a result

                        //Retrieve the result
                        ResultSet rs = stmt.getResultSet();

                        while(rs.next()) {

            				//Retrieve the last insert AUTO_INCREMENT value
                            id = rs.getLong(1);
                            if(id > 0)
                            	VehicleType.setId(id); // set this VehicleType's db id (proxy object)
                        }
                    }
                }
                else
                    throw new RARException("VehicleTypeManager.save: failed to save a vehicle type");
            }
            else {
                if( inscnt < 1 )
                    throw new RARException("VehicleTypeManager.save: failed to save an vehicle type"); 
            }
        }//try 
        catch (SQLException e)  {
        	e.printStackTrace();
            throw new RARException("VehicleTypeManager.save: failed to save an vehicle type: " + e);
        }
        throw new RARException( "VehicleTypeManager.restore: Could not restore persistent vehicle type object" );
        
    }
    
    public List<VehicleType> restore(VehicleType modelVehicleType) throws RARException {
    	
    	String       selectASQL = "SELECT  a.name " +
                " from vehicle_type a ";
		Statement    stmt = null;
		StringBuffer query = new StringBuffer(100);
		StringBuffer condition = new StringBuffer(100);
		List<VehicleType> vehicleTypes = new ArrayList<VehicleType>();
		
		condition.setLength(0);
		
		query.append(selectASQL);
		
		if (modelVehicleType != null) {
			if (modelVehicleType.getId() >= 0) {
				query.append(" WHERE id = " + modelVehicleType.getId());
			} else {
				if (modelVehicleType.getName() != null)
					condition.append(" AND hourly_price = '" + modelVehicleType.getName() + "'");
			} //else
		}//if
		
		try {
			
			stmt = conn.createStatement();
			
			if (stmt.execute(query.toString())) {
				String     name;
				VehicleType vehicleTypeProxy = null;
			    
				ResultSet rs = stmt.getResultSet();
				
				while (rs.next()) {
					name = rs.getString(1);
					
					vehicleTypeProxy = objectLayer.createVehicleType();
					vehicleTypeProxy.setName(name);
					
					vehicleTypes.add(vehicleTypeProxy);
				}
				return vehicleTypes;
			}
		} catch (SQLException e) {
			throw new RARException("modelVehicleTypeManager.restore: Could not restore persistent objects; Root cause: " + e );
		}
		throw new RARException( "modelVehicleTypeManager.restore: Could not restore persistent modelVehicleType object" );
	    
    }
    
    public void delete(VehicleType vehicleType) throws RARException {
    	String               deleteCommentSQL = "delete from VehicleType where id = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        if(!vehicleType.isPersistent()) return; //Jump out if not persistent, nothing to be deleted
        
        try {
            
            stmt = (PreparedStatement) conn.prepareStatement( deleteCommentSQL );
            stmt.setLong( 1, vehicleType.getId() );
            inscnt = stmt.executeUpdate();
            
            if (inscnt == 1) {
            	return;
            } else {
            	throw new RARException( "VehicleTypeManager.delete: failed to delete this vehicle type" );
            }
            
        }//try 
        catch( SQLException e ) {
            throw new RARException( "VehicleTypeManager.delete: failed to delete this vehicle type: " + e.getMessage() );
        }//catch
    }
}