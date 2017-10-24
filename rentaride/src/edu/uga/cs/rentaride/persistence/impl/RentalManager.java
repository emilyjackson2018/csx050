package edu.uga.cs.rentaride.persistence.impl;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import com.mysql.jdbc.PreparedStatement;
import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.object.ObjectLayer;


class RentalManager {
    private ObjectLayer objectLayer = null;
    private Connection   conn = null;

    public RentalManager(Connection conn, ObjectLayer objectLayer) {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }

    public void save(Rental rental)
            throws RARException
    {
        String               insertRSql = "insert into Rental (customer, pickupTime, returnTime, condition) values (?, ?, ?, ?)";
        String               updateRSql = "update Rental set customer, = ? pickupTime = ?, returnTime = ? where rentalNo = ? ";
        PreparedStatement    stmt = null;
        int                  inscnt;
        long                 RId;


        try {

            if(!rental.isPersistent())
                stmt = (PreparedStatement) conn.prepareStatement(insertRSql);
            else
                stmt = (PreparedStatement) conn.prepareStatement(updateRSql);

            if(rental.getCustomer() != null)
            	stmt.setString(1, rental.getCustomer().getUserName());
            else
                throw new RARException("Rental.save: can't save a Rental: Customer undefined");

            if(rental.getPickupTime() != null)
                stmt.setDate(2, (Date) rental.getPickupTime());
            else
                throw new RARException("Rental.save: can't save a rental: Pickup Time is not set or not persistent");

            if(rental.getReturnTime() != null)
                stmt.setDate(3, (Date) rental.getReturnTime());
            else
            	 throw new RARException("Rental.save: can't save a rental: Return Time is not set or not persistent");
       
            if(rental.getVehicle().getCondition() != null)
                stmt.setString(4, rental.getVehicle().getCondition().toString());
            else
            	 throw new RARException("rental.save: can't save a rental: Condition is not set or not persistent");
       
            
            if(rental.isPersistent())
                stmt.setLong(5, rental.getId());

            inscnt = stmt.executeUpdate();

            if(!rental.isPersistent()) {
                if(inscnt >= 1) {
                    String sql = "select last_insert_id()";
                    if(stmt.execute(sql)) {
                        ResultSet r = stmt.getResultSet();
                        while(r.next()) {
                            RId = r.getLong(1);
                            if(RId > 0)
                            	rental.setId(RId);
                        }
                    }
                }
                else
                    throw new RARException("RentalManager.save: failed to save a rental");
            }
            else {
                if(inscnt < 1)
                    throw new RARException("RentalManager.save: failed to save a rental");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new RARException("RentalManager.save: failed to save a Rental: " + e);
        }
    }

    public Iterator<Rental> restore(Rental rental)
            throws RARException
    {
        String       selectRSql = "select rent.customer, rent.pickupTime, rent.returnTime, rent.condition " +
                                      " from Rentals rent where 1 = 1 ";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);
        condition.setLength(0);
        query.append(selectRSql);

        if(rental != null) {
            if(rental.getId() >= 0){
                query.append(" and rentalNo = " + rental.getId());
            } else { 
            	if(rental.getCustomer() != null)
                condition.append(" and Customer = '" + rental.getCustomer().toString() + "'");
                        
                if(rental.getPickupTime() != null)
                    condition.append(" and Pickup Time = '" + rental.getPickupTime().toString() + "'");

                if(rental.getReturnTime() != null)
                    condition.append(" and Return Time = '" + rental.getPickupTime().toString() + "'");
       
                if(rental.getVehicle().getCondition() != null) {
                    condition.append(" Condition = '" + rental.getVehicle().getCondition().toString() + "'");
                }
            }
        }

        query.append(condition);
        
        try {

            stmt = conn.createStatement();

            if(stmt.execute(query.toString())) {
                ResultSet r = stmt.getResultSet();
                return new Rental(r, objectLayer);
            }
        }
        catch(Exception e) {
            throw new RARException("RentalManager.restore: Could not restore persistent Rental object; Root cause: " + e);
        }

        throw new RARException("RentalManager.restore: Could not restore persistent Rental object");
    }


    Iterator<Comment> restoreRentalComment(Rental rental)
            throws RARException
    {
        String       selectRSql = "select c.commendDate, c.comment, c.rental, rent.customer, rent.pickupTime, rent.returnTime, rent.condition " +
                                      " from Rentals rent, Comment c where 1 = 1 ";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);
        condition.setLength(0);
        query.append(selectRSql);

        if(rental != null) {
            if(rental.getId() >= 0){
                query.append(" and rentalNo = " + rental.getId());
            } else { 
            	if(rental.getCustomer() != null)
                condition.append(" and Customer = '" + rental.getCustomer().toString() + "'");
                        
                if(rental.getPickupTime() != null)
                    condition.append(" and Pickup Time = '" + rental.getPickupTime().toString() + "'");

                if(rental.getReturnTime() != null)
                    condition.append(" and Return Time = '" + rental.getPickupTime().toString() + "'");
       
                if(rental.getVehicle().getCondition() != null) {
                    condition.append(" Condition = '" + rental.getVehicle().getCondition().toString() + "'");
                }
            }
        }

        query.append(condition);
        
        try {

            stmt = conn.createStatement();

            if(stmt.execute(query.toString())) {
                ResultSet r = stmt.getResultSet();
                return new CommentIterator(r, objectLayer);
            }
        }
        catch(Exception e) {
            throw new RARException("RentalManager.restore: Could not restore persistent Comment object; Root cause: " + e);
        }

        throw new RARException("RentalManager.restore: Could not restore persistent Comment object");
    }


    Customer restoreRentalCustomer(Rental rental)
            throws RARException
    {
        String       selectRSql = "select rent.customer, rent.pickupTime, rent.returnTime, rent.condition " +
                                      " from Rentals rent where 1 = 1 ";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);

        // form the query based on the given Rental object instance
        query.append(selectRSql);

        if(rental != null) {
            if(rental.getId() >= 0){ // id is unique, so it is sufficient to get a Rental
                query.append(" and rentalNo = " + rental.getId());
            } else { 
            	if(rental.getCustomer() != null) // Type is unique, so it is sufficient to get a Rental
                condition.append(" and Customer = '" + rental.getCustomer().toString() + "'");
                        
                if(rental.getPickupTime() != null)
                    condition.append(" and Pickup Time = '" + rental.getPickupTime().toString() + "'");

                if(rental.getReturnTime() != null)
                    condition.append(" and Return Time = '" + rental.getPickupTime().toString() + "'");
       
                if(rental.getVehicle().getCondition() != null) {
                    condition.append(" Condition = '" + rental.getVehicle().getCondition().toString() + "'");
                }
            }
        }

        query.append(condition);
        
        try {

            stmt = conn.createStatement();

            if(stmt.execute(query.toString())) {
                ResultSet r = stmt.getResultSet();
                return new Rental(r, objectLayer);
            }
        }
        catch(Exception e) {
            throw new RARException("RentalManager.restore: Could not restore persistent Rental object; Root cause: " + e);
        }

        throw new RARException("RentalManager.restore: Could not restore persistent Rental object");
    }

    
    public void delete(Rental rental)
            throws RARException
    {
        String               deleteRSql = "delete from Rental where id = ?";
        PreparedStatement    stmt = null;
        int                  inscnt;

        if(!rental.isPersistent())
            return;

        try {
            stmt = (PreparedStatement) conn.prepareStatement(deleteRSql);
            stmt.setLong(1, rental.getId());
            inscnt = stmt.executeUpdate();
            if(inscnt == 1) {
                return;
            }
            else
                throw new RARException("RentalManager.delete: failed to delete a Rental");
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new RARException("RentalManager.delete: failed to delete a Rental: " + e);        }
    }
}


