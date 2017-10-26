package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;


class VehicleTypeManager
{
    private ObjectLayer objectLayer = null;
    private Connection   conn = null;

    public VehicleTypeManager(Connection conn, ObjectLayer objectLayer)
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }

    public void save(VehicleType vehicleType)
            throws RARException
    {
        String               insertVTSql = "insert into VehicleType (typeName ) values (?)";
        String               updateVTSql = "update VehicleTye set typeName = ? where id = ?";
        PreparedStatement    stmt = null;
        int                  inscnt;
        long                 VTId;

        try {

            if(!vehicleType.isPersistent())
                stmt = (PreparedStatement) conn.prepareStatement(insertVTSql);
            else
                stmt = (PreparedStatement) conn.prepareStatement(updateVTSql);

            if(vehicleType.getName() != null)
            	stmt.setString(1, vehicleType.getName());
            else
                throw new RARException("VehicleTypeManager.save: can't save a VehicleType: name undefined");
            if(vehicleType.isPersistent())
                stmt.setLong(2, vehicleType.getId());

            inscnt = stmt.executeUpdate();

            if(!vehicleType.isPersistent()) {
                if(inscnt >= 1) {
                    String sql = "select last_insert_id()";
                    if(stmt.execute(sql)) {
                        ResultSet r = stmt.getResultSet();
                        while(r.next()) {
                            VTId = r.getLong(1);
                            if(VTId > 0)
                                vehicleType.setId(VTId);
                        }
                    }
                }
                else
                    throw new RARException("VehicleTypeManager.save: failed to save a VehicleType");
            }
            else {
                if(inscnt < 1)
                    throw new RARException("VehicleTypeManager.save: failed to save a VehicleType");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new RARException("VehicleTypeManager.save: failed to save a VehicleType: " + e);
        }
    }

    public List<VehicleType> restore(VehicleType vehicleType)
            throws RARException
    {
        String       selectVTSql = "select vt.vehicleTypeId, vt.typeName " +
                                      " from VehicleType vt ";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);
        query.append(selectVTSql);

        if(vehicleType != null) {
            if(vehicleType.getId() >= 0)
                query.append(" and vehicleTypeId = " + vehicleType.getId());
            else if(vehicleType.getName() != null)
                query.append(" and typeName = '" + vehicleType.getName() + "'");
            else {
            }
        }

        try {

            stmt = conn.createStatement();
            if(stmt.execute(query.toString())) {
                ResultSet r = stmt.getResultSet();
                return (List<VehicleType>) new VehicleTypeList(r, objectLayer);
            }
        }
        catch(Exception e) {
            throw new RARException("VehicleTypeManager.restore: Could not restore persistent VehicleType object; Root cause: " + e);
        }

        throw new RARException("VehicleTypeManager.restore: Could not restore persistent VehicleType object");
    }

    public HourlyPriceList restoreVehicleTypeHourlyPrice(VehicleType vehicleType)
            throws RARException
    {
        String       selectPersonSql = " select hp.maxHours, hp.minHours, hp.price, " +
        							   "  vt.typeName, vt.vehicleTypeId from HourlyPrice hp, VehicleType vt where hp.hourlyPriceID = vt.vehicleTypeId";                    

        Statement    stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);
        
        query.append(selectPersonSql);
        
        if(vehicleType != null) {
            if(vehicleType.getId() >= 0)
                query.append(" and vt.vehicleTypeId = " + vehicleType.getId());
            else {

                if(vehicleType.getName() != null)
                    condition.append(" and vt.vehicleType = '" + vehicleType.getName() + "'");

               

                if(condition.length() > 0) {
                    query.append(condition);
                }
            }
        }
                
        try {

            stmt = conn.createStatement();
            if(stmt.execute(query.toString())) {
                ResultSet r = stmt.getResultSet();
                return new HourlyPriceList(r, objectLayer);

            }
        }
        catch(Exception e) {
            throw new RARException("VehicleManager.restoreReservationVehicleType: Could not restore persistent HourlyPrice object; Root cause: " + e);
        }

        throw new RARException("VehicleManager.restoreReservationVehicleType: Could not restore persistent HourlyPrice object");
    }
    
    public VehicleList restoreVehicleVehicleType(VehicleType vehicleType)
            throws RARException
    {
        String       selectPersonSql = "select v.registrationTag, v.lastService, v.make, v.mileage, v.model, v.rentalLocation, v.status, v.vehicleType, " +
        							   " v.vehicleYear, v.vehicleCondition, v.vehicleID" +
        							   "  vt.typeName, vt.vehicleTypeId from Vehicle v, VehicleType vt where v.vehicleID = vt.vehicleTypeId";                    

        Statement    stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);
        query.append(selectPersonSql);
        
        if(vehicleType != null) {
            if(vehicleType.getId() >= 0)
                query.append(" and vt.vehicleTypeId = " + vehicleType.getId());
            else {

                if(vehicleType.getName() != null)
                    condition.append(" and vt.vehicleType = '" + vehicleType.getName() + "'");

               

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
            throw new RARException("VehicleManager.restoreReservationVehicleType: Could not restore persistent Vehicle object; Root cause: " + e);
        }

        throw new RARException("VehicleManager.restoreReservationVehicleType: Could not restore persistent Vehicle object");
    }
    
    public ReservationList restoreReservationVehicleType(VehicleType vehicleType)
            throws RARException
    {
        String       selectPersonSql = "select r.customer, r.pickupTime, r.rental, r.rentalDuration, r.rentalLocation, r.vehicleType, r.reservationID, " + 
        							   "  vt.typeName, vt.vehicleTypeId from RentalLocations r, VehicleType vt where r.reservationID = vt.vehicleTypeId";                    
        Statement    stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);
        
        query.append(selectPersonSql);
        
        if(vehicleType != null) {
            if(vehicleType.getId() >= 0)
                query.append(" and vt.vehicleTypeId = " + vehicleType.getId());
            else {

                if(vehicleType.getName() != null)
                    condition.append(" and vt.vehicleType = '" + vehicleType.getName() + "'");

               

                if(condition.length() > 0) {
                    query.append(condition);
                }
            }
        }
                
        try {

            stmt = conn.createStatement();

            if(stmt.execute(query.toString())) {
                ResultSet r = stmt.getResultSet();
                return new ReservationList(r, objectLayer);

            }
        }
        catch(Exception e) {
            throw new RARException("VehicleManager.restoreReservationVehicleType: Could not restore persistent Reservation object; Root cause: " + e);
        }

        throw new RARException("VehicleManager.restoreReservationVehicleType: Could not restore persistent Reservation object");
    }
    
    
    public void delete(VehicleType vehicleType)
            throws RARException
    {
        String               deleteVTSql = "delete from VehicleType where vehicleTypeId = ?";
        PreparedStatement    stmt = null;
        int                  inscnt;

        if(!vehicleType.isPersistent())
            return;

        try {
            stmt = (PreparedStatement) conn.prepareStatement(deleteVTSql);
            stmt.setLong(1, vehicleType.getId());
            inscnt = stmt.executeUpdate();
            if(inscnt == 1) {
                return;
            }
            else
                throw new RARException("VehicleTypeManager.delete: failed to delete a VehcleType");
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new RARException("VehicleTypeManager.delete: failed to delete a VehicleType: " + e);        }
    }
}


