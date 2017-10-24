
package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Comment;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.UserStatus;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleCondition;
import edu.uga.cs.rentaride.entity.VehicleStatus;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.entity.impl.CommentImpl;
import edu.uga.cs.rentaride.object.ObjectLayer;


public class Rental
        implements Iterator<Rental> {
    private ResultSet   rs = null;
    private ObjectLayer objectLayer = null;
    private boolean     more = false;

    public Rental(ResultSet rs, ObjectLayer objectLayer)
            throws RARException
    {
        this.rs = rs;
        this.objectLayer = objectLayer;
        try {
            more = rs.next();
        }
        catch(Exception e) {
            throw new RARException("Rental: Cannot create an iterator; root cause: " + e);
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
        String cResidenceAddress;
        String cCardNumber;
        Date cCardExpiration; 
        String cFName;
        String cLName; 
        String cUName; 
        String cEmail;
        String cPassword; 
        Date cCreateDate;
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
        

        if(more) {

            try {
                rentalNo = rs.getLong("rentalNo");
                pickupTime = rs.getDate("pickupTime");
                rresid = rs.getLong("reservationID");
                rvecid = rs.getLong("vehicleID");
                custID = rs.getLong("id");
                cMembershipExpiration = null;
                cLicenseState = null;
                cLicenseNumber = null;
                cResidenceAddress = null;
                cCardNumber = null;
                cCardExpiration = null;
                cFName = rs.getString("firstName");
                cLName = rs.getString("lastName");
                cUName = rs.getString("userName"); 
                cEmail = rs.getString("emailAddress");
                cPassword = rs.getString("password"); 
                cCreateDate = rs.getDate("createdDate");
                vMake = rs.getString("make");
                vModel = rs.getString("model"); 
                year = rs.getInt("vehicleYear");
                vTag = rs.getString("registrationTag");
                vMileage = rs.getInt("mileage");
                vLastServiced = rs.getDate("lastService");
                resRentalDuration = 0; 
                
                more = rs.next();
            }
            catch(Exception e) {
                throw new NoSuchElementException("Rental: No next Rental object; root cause: " + e);
            }
            try {
            	vLocation = objectLayer.createRentalLocation(null, null, 0);
            	customer = objectLayer.createCustomer(cFName, cLName, cUName, cEmail, cPassword, cCreateDate, cMembershipExpiration, cLicenseState, cLicenseNumber, cResidenceAddress, cCardNumber, cCardExpiration);
            	customer.setId(custID);
            	vehicle = objectLayer.createVehicle(resVehicleType, vMake, vModel, year, vTag, vMileage, vLastServiced, vLocation, vCondition, vStatus);
            	vehicle.setId(rvecid);
				reservation = objectLayer.createReservation(resVehicleType, vLocation, resCustomer, vLastServiced, resRentalDuration);
				reservation.setId(rresid);
            } catch (RARException e) {
				e.printStackTrace();
			}
            
            try {
                rental = objectLayer.createRental(reservation, customer, vehicle, pickupTime);
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

