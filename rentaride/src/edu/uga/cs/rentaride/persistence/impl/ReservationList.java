package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.*;
import edu.uga.cs.rentaride.object.ObjectLayer;


public class ReservationList
    implements Iterator<Reservation> {
    private ResultSet   rs = null;
    private ObjectLayer objectLayer = null;
    private boolean     more = false;
    public ReservationList(ResultSet rs, ObjectLayer objectLayer)
            throws RARException
    { 
        this.rs = rs;
        this.objectLayer = objectLayer;
        try {
            more = rs.next();
        }
        catch(Exception e) {
            throw new RARException("Reservation: Cannot create an iterator; root cause: " + e);
        }
    }

    public boolean hasNext() 
    { 
        return more; 
    }

    public Reservation next() 
    {
    	long id;    
    	long r;
    	int rentalDuration;
    	String rl;
    	String vt;
    	String c;
    	Date pickupTime;
    	Rental rental = null;
    	RentalLocation rentalLocation = null;
    	VehicleType vehicleType = null;
    	Customer customer = null;
    	Reservation reservation = null;
    	List<HourlyPrice> priceList = null;
    	List<Vehicle> vehicleList = null;
    	List<Reservation> reservationList = null;
    	
        
        
        if(more) {

            try {
            	id = rs.getLong("id");
                pickupTime = rs.getDate("pickupTime");
                rentalDuration = rs.getInt("rentalDuration");
                rl = rs.getString("rentalLocation");
                vt = rs.getString("vehicleType");
                c = rs.getString("customer");
                r = rs.getLong("rental");
                more = rs.next();
            }
            catch(Exception e) {
                throw new NoSuchElementException("Reservation: No next Reservation object; root cause: " + e);
            }
            
            try {
				vehicleType = objectLayer.createVehicleType(vt, priceList, vehicleList, reservationList);
			} catch (RARException e1) {
				throw new NoSuchElementException("Reservation: No next Reservation object; root cause: " + e1);
			}
            
            try {
				rentalLocation = objectLayer.createRentalLocation(rl, null, -1);
			} catch (RARException e) {
				throw new NoSuchElementException("Reservation: No next Reservation object; root cause: " + e);
			} 
            customer = objectLayer.createCustomer();
            try {
                reservation = objectLayer.createReservation(pickupTime,  rentalDuration,  customer, vehicleType, rentalLocation, rental);
                reservation.setId(id);
            }
            catch(RARException ce) {
                ce.printStackTrace();
                System.out.println(ce);
            }

            return reservation;
        }
        else {
            throw new NoSuchElementException("Reservation: No next Reservation object");
        }
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }

}

