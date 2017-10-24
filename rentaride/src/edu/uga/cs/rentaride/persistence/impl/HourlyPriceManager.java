package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import com.mysql.jdbc.PreparedStatement;
import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;


class HourlyPriceManager {
    private ObjectLayer objectLayer = null;
    private Connection   conn = null;
    
    public HourlyPriceManager(Connection conn, ObjectLayer objectLayer) {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void save(HourlyPrice hourlyPrice) throws RARException {
        String               insertHPSql = "insert into HourlyPrice (maxHours, minHours, price, hourlyPriceID) values (?, ?, ?, ?)";
        String               updateHPSql = "update HourlyPrice set maxHours = ?, minHours = ?, price = ?, hourlyPriceID = ? where id = ?";
        PreparedStatement    stmt = null;
        int                  inscnt;
        long                 HPId;
                 
        try {

            if(!hourlyPrice.isPersistent())
                stmt = (PreparedStatement) conn.prepareStatement(insertHPSql);
            else
                stmt = (PreparedStatement) conn.prepareStatement(updateHPSql);

            if(hourlyPrice.getMaxHours()!= 0)
                stmt.setInt(1, hourlyPrice.getMaxHours());
            else 
                throw new RARException("HourlyPriceManager.save: can't save an HourlyPrice: max price undefined");
            
            if(hourlyPrice.getMinHours() != 0)
                stmt.setInt(2, hourlyPrice.getMinHours());
            else 
                throw new RARException("HourlyPriceManager.save: can't save an HourlyPrice: min price undefined");
            
            if(hourlyPrice.getPrice() == 0){
            	stmt.setInt(3, hourlyPrice.getPrice());
            } else {
            	stmt.setNull(3, java.sql.Types.INTEGER);
            }
            
            if(hourlyPrice.isPersistent())
                stmt.setLong(4, hourlyPrice.getId());

            inscnt = stmt.executeUpdate();

            if(!hourlyPrice.isPersistent()) {
                if(inscnt >= 1) {
                    String sql = "select last_insert_id()";
                    if(stmt.execute(sql)) {
                        ResultSet r = stmt.getResultSet();
                        while(r.next()) {
                            HPId = r.getLong(1);
                            if(HPId > 0)
                            	hourlyPrice.setId(HPId);
                        }
                    }
                }
                else
                    throw new RARException("HourlyPriceManager.save: failed to save an HourlyPrice");
            }
            else {
                if(inscnt < 1)
                    throw new RARException("HourlyPriceManager.save: failed to save an HourlyPrice");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new RARException("HourlyPriceManager.save: failed to save a HourlyPrice: " + e);
        }
    }

    public Iterator<HourlyPrice> restore(HourlyPrice hourlyPrice) 
            throws RARException
    {
        String       selectHPSql = "select hp.maxHours, hp.minHours, hp.price from HourlyPrice hp where hourlyPriceID = id ";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);
        condition.setLength(0);
        query.append(selectHPSql);
        
        if(hourlyPrice != null) {
            if(hourlyPrice.getId() >= 0)
                query.append(" and hourlyPriceID = " + hourlyPrice.getId());
            else {

                if(hourlyPrice.getMaxHours() != 0)
                	query.append(" and maxHours = '" + hourlyPrice.getMaxHours() + "'");

                if(hourlyPrice.getMinHours() != 0) {
                    query.append(" and minHours = '" + hourlyPrice.getMinHours() + "'");
                }
            }
        }
        
        try {

            stmt = conn.createStatement();
            if(stmt.execute(query.toString())) {
                ResultSet r = stmt.getResultSet();
                return new HourlyPrice(r, objectLayer);
            }
        }
        catch(Exception e) {
            throw new RARException("HourlyPriceManager.restore: Could not restore persistent HourlyPrice object; Root cause: " + e);
        }

        throw new RARException("HourlyPriceManager.restore: Could not restore persistent HourlyPrice object");
    }
    
       VehicleType restoreVehicleTypeHourlyPrice(HourlyPrice hourlyPrice)
            throws RARException
    {
        String       selectHPSql = "select vt.typeName, hp.maxHours, hp.minHours, hp.price from HourlyPrice hp, VehicleType vt where hp.hourlyPriceID = vt.vehicleTypeId ";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);
        
	query.append(selectHPSql);
        
        if(hourlyPrice != null) {
            if(hourlyPrice.getId() >= 0)
                query.append(" and hourlyPriceID = " + hourlyPrice.getId());
            else {

                if(hourlyPrice.getMaxHours() != 0)
                	query.append(" and maxHours = '" + hourlyPrice.getMaxHours() + "'");

                if(hourlyPrice.getMinHours() != 0) {
                    query.append(" and minHours = '" + hourlyPrice.getMinHours() + "'");
                }
            }
        }
        
        try {

            stmt = conn.createStatement();

            if(stmt.execute(query.toString())) {
                ResultSet r = stmt.getResultSet();
                return new VehicleTypeIterator(r, objectLayer).next();
            }
        }
        catch(Exception e) {
            throw new RARException("HourlyPriceManager.restore: Could not restore persistent VehicleType object; Root cause: " + e);
        }

        throw new RARException("HourlyPriceManager.restore: Could not restore persistent VehicleType object");
    }


    public void delete(HourlyPrice hourlyPrice) 
            throws RARException
    {
        String               deleteHPSql = "delete from HourlyPrice where id = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
             
        if(!hourlyPrice.isPersistent())
            return;
        
        try {
            stmt = (PreparedStatement) conn.prepareStatement(deleteHPSql);
            stmt.setLong(1, hourlyPrice.getId());
            inscnt = stmt.executeUpdate();          
            if(inscnt == 1) {
                return;
            }
            else
                throw new RARException("HourlyPriceManager.delete: failed to delete a HourlyPrice");
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new RARException("HourlyPriceManager.delete: failed to delete a HourlyPrice: " + e);        }
    }
}


