package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.object.*;
import edu.uga.cs.rentaride.persistence.Persistable;
import edu.uga.cs.rentaride.entity.*;

public class VehicleList
    implements Iterator<Vehicle>
{
    private ResultSet   rs = null;
    private ObjectLayer objectLayer = null;
    private boolean     more = false;
    public VehicleList(ResultSet rs, ObjectLayer objectLayer)
            throws RARException
    { 
        this.rs = rs;
        this.objectLayer = objectLayer;
        try {
            more = rs.next();
        }
        catch(Exception e) {
            throw new RARException("Vehicle: Cannot create an iterator; root cause: " + e);
        }
    }

    public boolean hasNext() 
    { 
        return more; 
    }

    public Vehicle next() 
    {
    	long id;    
    	Date lastServiced;
        int mileage;
        int year;
        String model;
        String make;
        String registrationTag;
        String RL;
        String VS;
        String VT;
        String VC;
        RentalLocation rentalLocation = null; 
        VehicleStatus vehicleStatus = null;
        VehicleType vehicleType = null;
        VehicleCondition vehicleCondition = null;
        Vehicle vehicle = null;
        List <Vehicle> vehicleList = null;
        List <HourlyPrice> priceList = null;
        List <Reservation> resList = null;
        List <Rental> rentalList = null;
        
        
        if(more) {

            try {
            	id = rs.getLong("vehicleID");
                registrationTag = rs.getString("registrationTag");
                lastServiced = rs.getDate("lastService");
                make = rs.getString("make");
                mileage = rs.getInt("mileage");
                model = rs.getString("model");
                RL = rs.getString("rentalLocation");
                VS = rs.getString("status");
                VT = rs.getString("vehicleType");
                year = rs.getInt("vehicleYear");
                VC = rs.getString("vehicleCondition");
                more = rs.next();
            }
            catch(Exception e) {
                throw new NoSuchElementException("Vehicle: No next Vehicle object; root cause: " + e);
            }
            
            
            try {
				vehicleType = objectLayer.createVehicleType(VT);
			} catch (RARException e) {
				throw new NoSuchElementException("Vehicle: No VehicleType object; root cause: " + e);
			} 
            try {
				rentalLocation = objectLayer.createRentalLocation(RL, null, -1);
			} catch (RARException e) {
				throw new NoSuchElementException("Vehicle: No RenalLocation object; root cause: " + e);
			}
            vehicleCondition = VehicleCondition.valueOf(VC);
            vehicleStatus = VehicleStatus.valueOf(VS);
            try {
                vehicle = objectLayer.createVehicle(make, model, year, registrationTag, mileage, lastServiced, vehicleType, rentalLocation, vehicleCondition, vehicleStatus); 
                ((Persistable) vehicle).setId(id);
            }
            catch(RARException ce) {
                ce.printStackTrace();
                System.out.println(ce);
            }

            return vehicle;
        }
        else {
            throw new NoSuchElementException("Vehicle: No next Vehicle object");
        }
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }

}

