
package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.*;
import edu.uga.cs.rentaride.entity.impl.CommentImpl;
import edu.uga.cs.rentaride.object.ObjectLayer;


public class RentalList
        implements Iterator<Rental> {
    private ResultSet   rs = null;
    private ObjectLayer objectLayer = null;
    private boolean     more = false;

    public RentalList(ResultSet rs, ObjectLayer objectLayer)
            throws RARException
    {
        this.rs = rs;
        this.objectLayer = objectLayer;
        try {
            more = rs.next();
        }
        catch(Exception e) {
            throw new RARException("Rental: Cannot create a list; root cause: " + e);
        }
    }

    public boolean hasNext()
    {
        return more;
    }

    public Rental next()
    {
        long rentalNo;
        Rental rental = null;
        Date pickupTime; 
        Reservation reservation = null;
        long rresid;
        Vehicle vehicle = null;
        long rvecid;
        Customer customer = null;
        long custID;
        Date cMembershipExpiration;
        String cLicenseState;
        String cLicenseNumber;
        String cCardNumber;
        Date cCardExpiration; 
        String vMake;
        String vModel; 
        int year;
        String vTag;
        int vMileage;
        Date vLastServiced; 
        RentalLocation vLocation = null;
        VehicleCondition vCondition = null;
        VehicleStatus vStatus = null;
        int resRentalDuration; 
        Customer resCustomer = null; 
        VehicleType resVehicleType = null; 
        Date returnTime;
        boolean late;
        int charge;
        CommentImpl comment = null;
        RentalLocation location = null;
        

        if(more) {

            try {
                rentalNo = rs.getLong("rentalNo");
                rresid = rs.getLong("reservationID");
                rvecid = rs.getLong("vehicleID");
                custID = rs.getLong("id");
                cMembershipExpiration = null;
                cLicenseState = null;
                cLicenseNumber = null;
                cCardNumber = null;
                cCardExpiration = null;
                vMake = rs.getString("make");
                vModel = rs.getString("model"); 
                year = rs.getInt("vehicleYear");
                vTag = rs.getString("registrationTag");
                vMileage = rs.getInt("mileage");
                vLastServiced = rs.getDate("lastService");
                resRentalDuration = 0; 
                
            	pickupTime = rs.getDate("pickupTime");
            	returnTime = rs.getDate("returnTime");
            	late = rs.getBoolean("late");
            	charge = rs.getInt("charge");
                
                more = rs.next();
            }
            catch(Exception e) {
                throw new NoSuchElementException("Rental: No next Rental object; root cause: " + e);
            }
            try {
            	vLocation = objectLayer.createRentalLocation(null, null, 0);
            	customer = objectLayer.createCustomer(cMembershipExpiration, cLicenseState, cLicenseNumber, cCardNumber, cCardExpiration, (List<Reservation>) reservation, (List<Comment>) comment, (List<Rental>) rental);
            	customer.setId(custID);
            	vehicle = objectLayer.createVehicle(vMake, vModel, year, vTag, vMileage, vLastServiced, vStatus, vCondition, resVehicleType, location, (List<Rental>) rental);
            	vehicle.setId(rvecid);
				reservation = objectLayer.createReservation(pickupTime, resRentalDuration, resCustomer, resVehicleType, location, rental);
				reservation.setId(rresid);
            } catch (RARException e) {
				e.printStackTrace();
			}
         
            
            try {
                rental = objectLayer.createRental(pickupTime, returnTime, late, charge, reservation, vehicle, customer, comment);
                rental.setId(rentalNo);
            }
            catch(RARException ce) {
                ce.printStackTrace();
                System.out.println(ce);
            }
            

            return rental;
        }
        else {
            throw new NoSuchElementException("Rental: No next Rental object");
        }
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }

}

