package edu.uga.cs.rentaride.persistence.impl;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.object.ObjectLayer;


class RentalLocationManager
{
    private ObjectLayer objectLayer = null;
    private Connection   conn = null;

    public RentalLocationManager(Connection conn, ObjectLayer objectLayer)
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }

    public void save(RentalLocation rentalLocation)
            throws RARException
    {
        String               insertRLSql = "insert into RentalLocations (address, locationName, capacity) values (?, ?, ?)";
        String               updateRLSql = "update RentalLocations set address = ?, locationName = ?, capacity = ? where rentalID = ? ";
        PreparedStatement    stmt = null;
        int                  inscnt;
        long                 RLId;


        try {

            if(!rentalLocation.isPersistent())
                stmt = (PreparedStatement) conn.prepareStatement(insertRLSql);
            else
                stmt = (PreparedStatement) conn.prepareStatement(updateRLSql);

            if(rentalLocation.getAddress() != null)
            	stmt.setString(1, rentalLocation.getAddress());
            else
                throw new RARException("RentalLocation.save: can't save a RentalLocation: address undefined");

            if(rentalLocation.getName() != null)
                stmt.setString(2, rentalLocation.getName());
            else
                throw new RARException("rentalLocation.save: can't save a rentalLocation: locationName is not set or not persistent");

            if(rentalLocation.getCapacity() != 0)
                stmt.setInt(3, rentalLocation.getCapacity());
            else
                stmt.setNull(3, java.sql.Types.INTEGER);
       
            
            if(rentalLocation.isPersistent())
                stmt.setLong(4, rentalLocation.getId());

            inscnt = stmt.executeUpdate();

            if(!rentalLocation.isPersistent()) {
                if(inscnt >= 1) {
                    String sql = "select last_insert_id()";
                    if(stmt.execute(sql)) {
                        ResultSet r = stmt.getResultSet();
                        while(r.next()) {
                            RLId = r.getLong(1);
                            if(RLId > 0)
                            	rentalLocation.setId(RLId);
                        }
                    }
                }
                else
                    throw new RARException("RentalLocationManager.save: failed to save a rentalLocation");
            }
            else {
                if(inscnt < 1)
                    throw new RARException("RentalLocationManager.save: failed to save a rentalLocation");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new RARException("RentalLocationManager.save: failed to save a RentalLocation: " + e);
        }
    }

    public List<RentalLocation> restore(RentalLocation rentalLocation)
            throws RARException
    {
    	String       selectRLSql = "select rl.rentalID ,rl.address, rl.capacity, rl.locationName " +
                " from RentalLocations rl ";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);
        query.append(selectRLSql);

        if(rentalLocation != null) {
            if(rentalLocation.getId() >= 0){
                query.append(" and rentalID = " + rentalLocation.getId());
            } else { 
            	if(rentalLocation.getName() != null)
                condition.append(" and locationName = '" + rentalLocation.getName() + "'");
                        
                if(rentalLocation.getAddress() != null)
                    condition.append(" and address = '" + rentalLocation.getAddress() + "'");

                if(rentalLocation.getCapacity() != 0) {
                    if(condition.length() > 0)
                        condition.append(" and");
                    condition.append(" established = '" + rentalLocation.getCapacity() + "'");
                }    
                    query.append(" where ");
                    query.append(condition);
            }
        }

        try {

            stmt = conn.createStatement();
            if(stmt.execute(query.toString())) {
                ResultSet r = stmt.getResultSet();
                return (List<RentalLocation>) new RentalLocationList(r, objectLayer);
            }
        }
        catch(Exception e) {
            throw new RARException("RentalLocationManager.restore: Could not restore persistent RentalLocation object; Root cause: " + e);
        }

        throw new RARException("RentalLocationManager.restore: Could not restore persistent RentalLocation object");
    }

    
    
    
    
    public Iterator<Vehicle> restoreVehicleRentalLocation(RentalLocation rentalLocation)
            throws RARException
    {
        String selectPersonSql = "select rl.address, rl.capacity, rl.locationName, v.reservationTag, v.lastService, v.make, v.milage, v.model, v.rentalLocation, v.status, v.vehicleType, v.vehicleYear, v.vehicleCondition " +
                                 "from vehicle v, rentalLocation rl where v.vehicleID = rl.rentalID";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);
        
        // form the query based on the given Person object instance
        query.append(selectPersonSql);
        
        if(rentalLocation != null) {
            
        	if(rentalLocation.getId() >= 0) // id is unique, so it is sufficient to get a rentalLocation
                query.append(" and rl.rentalID = " + rentalLocation.getId());
        	else if(rentalLocation.getName() != null)
                query.append(" and rl.locationName = '" + rentalLocation.getName() + "'");
            else {
                if(rentalLocation.getAddress() != null)
                    condition.append(" rl.address = '" + rentalLocation.getAddress() + "'");
                else
                    condition.append(" AND rl.address = '" + rentalLocation.getAddress() + "'");
                
                if(rentalLocation.getCapacity() != 0 && condition.length() == 0)
                    condition.append(" rl.capacity = '" + rentalLocation.getCapacity() + "'");
                else
                    condition.append(" AND rl.capacity = '" + rentalLocation.getCapacity() + "'");
                
                if(condition.length() > 0) {
                    query.append(condition);
                }
            }
        }
                
        try {

            stmt = conn.createStatement();

            if(stmt.execute(query.toString())) {
                ResultSet r = stmt.getResultSet();
                return new VehicleList(r, objectLayer);
            }
        }
        catch(Exception e) {
            throw new RARException("RentalLocationManager.restoreVehicleRentalLocation: Could not restore persistent Vehicle objects; Root cause: " + e);
        }

        throw new RARException("RentalLocationManager.restoreVehicleRentalLocation: Could not restore persistent Vehicle objects");
    }
    
    
    
    public void delete(RentalLocation rentalLocation)
            throws RARException
    {
        String               deleteRLSql = "delete from RentalLocation where id = ?";
        PreparedStatement    stmt = null;
        int                  inscnt;

        if(!rentalLocation.isPersistent())
            return;

        try {
            stmt = (PreparedStatement) conn.prepareStatement(deleteRLSql);
            stmt.setLong(1, rentalLocation.getId());
            inscnt = stmt.executeUpdate();
            if(inscnt == 1) {
                return;
            }
            else
                throw new RARException("RentalLocationManager.delete: failed to delete a RentalLocation");
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new RARException("RentalLocationManager.delete: failed to delete a RentalLocation: " + e);        }
    }
}


