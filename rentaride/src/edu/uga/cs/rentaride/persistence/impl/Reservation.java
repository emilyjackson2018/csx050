package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.VehicleCondition;
import edu.uga.cs.rentaride.entity.VehicleStatus;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.object.ObjectLayer;


public class Reservation
    implements Iterator<Reservation> {
    private ResultSet   rs = null;
    private ObjectLayer objectLayer = null;
    private boolean     more = false;
    public Reservation(ResultSet rs, ObjectLayer objectLayer)
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
            
            vehicleType = objectLayer.createVehicleType(vt);
            rentalLocation = objectLayer.createRentalLocation(rl, null, -1); 
            customer = objectLayer.createCustomer(null, null, c, null, null, null, null, null, null, null, null, null);
            try {
                reservation = objectLayer.createReservation(vehicleType,  rentalLocation,  customer, pickupTime, rentalDuration);
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

