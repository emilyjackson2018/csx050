package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.*;
import edu.uga.cs.rentaride.object.ObjectLayer;


class VehicleManager {
    private ObjectLayer objectLayer = null;
    private Connection   conn = null;
    
    public VehicleManager(Connection conn, ObjectLayer objectLayer) {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void save(Vehicle vehicle) 
            throws RARException
    {
        String               insertVSql = "insert into Vehicle (registrationTag, lastService, make, mileage, model, rentalLocation, status, " +
        								  " vehicleType, vehicleYear, vehicleCondition, vehicleID) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String               updateVSql = "update Vehicle set registrationTag = ?, lastService = ?, make = ?, mileage = ?, model = ?, " + 
        								  " rentalLocation = ?, status = ?, vehicleType = ?, vehicleYear = ?, vehicleCondition = ? where id = ?";
        PreparedStatement    stmt = null;
        int                  inscnt;
        long                 VId;
        try {

            if(!vehicle.isPersistent())
                stmt = (PreparedStatement) conn.prepareStatement(insertVSql);
            else
                stmt = (PreparedStatement) conn.prepareStatement(updateVSql);

            if(vehicle.getRegistrationTag() != null)
                stmt.setString(1, vehicle.getRegistrationTag());
            else 
                throw new RARException("VenhicleManager.save: can't save a Vehicle: name undefined");

            if(vehicle.getLastServiced() != null)
                stmt.setDate(2, new Date(vehicle.getLastServiced().getTime()));
            else
                stmt.setNull(2, java.sql.Types.DATE);
            
            if(vehicle.getMake() != null)
                stmt.setString(3, vehicle.getMake());
            else
                stmt.setNull(3, java.sql.Types.VARCHAR);
            
            if(vehicle.getMileage() != 0)
                stmt.setInt(4, vehicle.getMileage());
            else
                stmt.setNull(4, java.sql.Types.INTEGER);
            
            if(vehicle.getModel() != null)
                stmt.setString(5, vehicle.getModel());
            else
                stmt.setNull(5, java.sql.Types.VARCHAR);
            
            if(vehicle.getRentalLocation() != null)
                stmt.setString(6, vehicle.getRentalLocation().getName());
            else
                stmt.setNull(6, java.sql.Types.VARCHAR);
            
            if(vehicle.getStatus() != null)
                stmt.setString(7, vehicle.getStatus().toString());
            else
                stmt.setNull(7, java.sql.Types.VARCHAR);
            
            if(vehicle.getVehicleType() != null)
                stmt.setString(8, vehicle.getVehicleType().getName());
            else
                stmt.setNull(8, java.sql.Types.VARCHAR);
            
            if(vehicle.getYear() != 0)
                stmt.setInt(9, vehicle.getYear());
            else
                stmt.setNull(9, java.sql.Types.INTEGER);
            
            if(vehicle.getCondition() != null)
                stmt.setString(10, vehicle.getCondition().toString());
            else
                stmt.setNull(10, java.sql.Types.VARCHAR);

            if(vehicle.isPersistent()) {
                stmt.setLong(11, vehicle.getId());
            } else {
            	
            	stmt.setLong(11, 0);
            }
         
            inscnt = stmt.executeUpdate();

            if(!vehicle.isPersistent()) {
                if(inscnt >= 1) {
                    String sql = "select last_insert_id()";
                    if(stmt.execute(sql)) {
                        ResultSet r = stmt.getResultSet();
                        while(r.next()) {
                            VId = r.getLong(1);
                            if(VId > 0)
                            	vehicle.setId(VId);
                        }
                    }
                }
                else
                    throw new RARException("VehicleManager.save: failed to save a Vehicle");
            }
            else {
                if(inscnt < 1)
                    throw new RARException("VehicleManager.save: failed to save a Vehicle");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new RARException("VehicleManager.save: failed to save a Vehicle: " + e);
        }
    }

    public List<Vehicle> restore(Vehicle vehicle) 
            throws RARException
    {
        String       selectVSql = "select v.registrationTag, v.lastService, v.make, v.mileage, v.model, v.rentalLocation, " + 
        							 " v.status, v.vehicleType, v.vehicleYear, v.vehicleCondition, v.vehicleID from Vehicle v where 1=1" ;
        Statement    stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);
        condition.setLength(0);
        query.append(selectVSql);
        
        if(vehicle != null) {
            if(vehicle.getId() >= 0)
                query.append(" and id = " + vehicle.getId());
            else if(vehicle.getRegistrationTag() != null)
                query.append(" and registrationTag = '" + vehicle.getRegistrationTag() + "'");
            else {

                if(vehicle.getLastServiced() != null)
                    query.append(" and lastService = '" + vehicle.getLastServiced() + "'");
                
                if(vehicle.getMake() != null)
                    query.append(" and make = '" + vehicle.getMake() + "'");
                
                if(vehicle.getMileage() != 0)
                    query.append(" and mileage = '" + vehicle.getMileage() + "'");
                
                if(vehicle.getModel() != null)
                    query.append(" and model = '" + vehicle.getModel() + "'");
                
                if(vehicle.getRentalLocation() != null)
                    query.append(" and rentalLocation = '" + vehicle.getRentalLocation() + "'");
                
                if(vehicle.getStatus() != null)
                    query.append(" and status = '" + vehicle.getStatus() + "'");
                
                if(vehicle.getVehicleType() != null)
                    query.append(" and vehicleType = '" + vehicle.getVehicleType() + "'");
                
                if(vehicle.getYear()!= 0)
                    query.append(" and vehicleYear = '" + vehicle.getYear() + "'");
                
                if(vehicle.getCondition() != null)
                    query.append(" and vehicleCondition = '" + vehicle.getCondition() + "'");
                
            }
        }
        
        try {

            stmt = conn.createStatement();
            System.out.println("stmt: " + query);
            if(stmt.execute(query.toString())) {
                ResultSet r = stmt.getResultSet();
                return (List<Vehicle>) new VehicleList(r, objectLayer);
            }
        }
        catch(Exception e) {
            throw new RARException("VehicleManager.restore: Could not restore persistent Vehicle object; Root cause: " + e);
        }

        throw new RARException("VehicleManager.restore: Could not restore persistent Vehicle object");
    }
    
    RentalLocation restoreVehicleRentalLocation(Vehicle vehicle)
            throws RARException
    {
        String       selectRLSql = "select rl.address, rl.capacity, rl.locationName, v.registrationTag, v.lastService, v.make, v.mileage, v.model, v.rentalLocation, " + 
        							 " v.status, v.vehicleType, v.vehicleYear, v.vehicleCondition, v.vehicleID, from Vehicle v, RentalLocations rl where v.vehicleId = rl.renatalID " ;
        Statement    stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);
        condition.setLength(0);
        query.append(selectRLSql);
        
        if(vehicle != null) {
            if(vehicle.getId() >= 0)
                query.append(" and id = " + vehicle.getId());
            else if(vehicle.getRegistrationTag() != null)
                query.append(" and registrationTag = '" + vehicle.getRegistrationTag() + "'");
            else {

                if(vehicle.getLastServiced() != null)
                    query.append(" and lastService = '" + vehicle.getLastServiced() + "'");
                
                if(vehicle.getMake() != null)
                    query.append(" and make = '" + vehicle.getMake() + "'");
                
                if(vehicle.getMileage() != 0)
                    query.append(" and mileage = '" + vehicle.getMileage() + "'");
                
                if(vehicle.getModel() != null)
                    query.append(" and model = '" + vehicle.getModel() + "'");
                
                if(vehicle.getRentalLocation() != null)
                    query.append(" and rentalLocation = '" + vehicle.getRentalLocation() + "'");
                
                if(vehicle.getStatus() != null)
                    query.append(" and status = '" + vehicle.getStatus() + "'");
                
                if(vehicle.getVehicleType() != null)
                    query.append(" and vehicleType = '" + vehicle.getVehicleType() + "'");
                
                if(vehicle.getYear()!= 0)
                    query.append(" and vehicleYear = '" + vehicle.getYear() + "'");
                
                if(vehicle.getCondition() != null)
                    query.append(" and vehicleCondition = '" + vehicle.getCondition() + "'");
                
            }
        }
        
        try {

            stmt = conn.createStatement();
            if(stmt.execute(query.toString())) {
                ResultSet r = stmt.getResultSet();
                return (RentalLocation) new RentalLocationList(r, objectLayer);
            }
        }
        catch(Exception e) {
            throw new RARException("VehicleManager.restore: Could not restore persistent RenatlLocation object; Root cause: " + e);
        }

        throw new RARException("VehicleManager.restore: Could not restore persistent RentalLocation object");
    }

 VehicleType restoreVehicleVehicleType(Vehicle vehicle)
            throws RARException
    {
        String       selectVTSql = "select vt.typeName, v.registrationTag, v.lastService, v.make, v.mileage, v.model, v.rentalLocation, " + 
        							 " v.status, v.vehicleType, v.vehicleYear, v.vehicleCondition, v.vehicleID, from Vehicle v, VehicleType vt where v.vehicleId = vt.vehicleTypeId " ;
        Statement    stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);
        condition.setLength(0);
        query.append(selectVTSql);
        
        if(vehicle != null) {
            if(vehicle.getId() >= 0)
                query.append(" and id = " + vehicle.getId());
            else if(vehicle.getRegistrationTag() != null)
                query.append(" and registrationTag = '" + vehicle.getRegistrationTag() + "'");
            else {

                if(vehicle.getLastServiced() != null)
                    query.append(" and lastService = '" + vehicle.getLastServiced() + "'");
                
                if(vehicle.getMake() != null)
                    query.append(" and make = '" + vehicle.getMake() + "'");
                
                if(vehicle.getMileage() != 0)
                    query.append(" and mileage = '" + vehicle.getMileage() + "'");
                
                if(vehicle.getModel() != null)
                    query.append(" and model = '" + vehicle.getModel() + "'");
                
                if(vehicle.getRentalLocation() != null)
                    query.append(" and rentalLocation = '" + vehicle.getRentalLocation() + "'");
                
                if(vehicle.getStatus() != null)
                    query.append(" and status = '" + vehicle.getStatus() + "'");
                
                if(vehicle.getVehicleType() != null)
                    query.append(" and vehicleType = '" + vehicle.getVehicleType() + "'");
                
                if(vehicle.getYear()!= 0)
                    query.append(" and vehicleYear = '" + vehicle.getYear() + "'");
                
                if(vehicle.getCondition() != null)
                    query.append(" and vehicleCondition = '" + vehicle.getCondition() + "'");
            }
        }
        
        try {

            stmt = conn.createStatement();
            if(stmt.execute(query.toString())) {
                ResultSet r = stmt.getResultSet();
                return (VehicleType) new VehicleTypeList(r, objectLayer);
            }
        }
        catch(Exception e) {
            throw new RARException("VehicleManager.restore: Could not restore persistent VehicleType object; Root cause: " + e);
        }

        throw new RARException("VehicleManager.restore: Could not restore persistent VehicleType object");
    }


    public void delete(Vehicle vehicle) 
            throws RARException
    {
        String               deleteVSql = "delete from Vehicle where id = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
             
        if(!vehicle.isPersistent())
            return;
        
        try {
            stmt = (PreparedStatement) conn.prepareStatement(deleteVSql);
            stmt.setLong(1, vehicle.getId());
            inscnt = stmt.executeUpdate();          
            if(inscnt == 1) {
                return;
            }
            else
                throw new RARException("VehicleManager.delete: failed to delete a Vehicle");
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new RARException("VehicleManager.delete: failed to delete a Vehicle: " + e);        }
    }
}

