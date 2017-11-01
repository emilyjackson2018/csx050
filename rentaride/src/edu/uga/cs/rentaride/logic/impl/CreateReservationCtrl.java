package edu.uga.cs.rentaride.logic.impl;


import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.object.ObjectLayer;



public class CreateReservationCtrl {
    
    private ObjectLayer objectLayer = null;
    
    public CreateReservationCtrl(ObjectLayer objectModel)
    {
        this.objectLayer = objectModel;
    }
    
    public long createReservation(String pickupTime, int rentalDuration, String rentalLocationStr, long customerId, String vehicleTypeStr)
            throws RARException
    { 
        Date 		                   reservationTime = null;
        Reservation 		           reservation  = null;
        Customer                   customer = null;
        Customer                   modelCustomer = null;
        List<Customer>         customers = null;
        RentalLocation                   rentalLocation = null;
        RentalLocation                   modelRentalLocation = null;
        List<RentalLocation>         rentalLocations = null;
        VehicleType                   vehicleType = null;
        VehicleType                   modelVehicleType = null;
        List<VehicleType>         vehicleTypes = null;
        

        modelCustomer = objectLayer.createCustomer();
        modelCustomer.setId(customerId);
        customers = objectLayer.findCustomer(modelCustomer);
        while(customers.size() > 0) {
            customer = persons.get();
        }

        // check if the person actually exists
        if(customer == null)
            throw new RARException("A person with this id does not exist: " + customerId);

        // retrieve the rental location 
        modelRentalLocation = objectLayer.createRentalLocation();
        modelRentalLocation.setName(rentalLocationStr);
        rentalLocations = objectLayer.findRentalLocation(modelRentalLocation);
        while(rentalLocations.size() > 0) {
            rentalLocation = persons.get();
        }

        // check if the rental location actually exists
        if(rentalLocation == null)
            throw new RARException("A Rental location with this name does not exist: " + rentalLocationStr);
            
        // retrieve the founder person
        modelVehicleType = objectLayer.createVehicleType();
        modelVehicleType.setType(vehicleTypeStr);
        vehicleTypes = objectLayer.findVehicleType(modelVehicleType);
        while(vehicleTypes.size() > 0) {
            vehicleType = vehicleTypes.get();
        }

        // check if the vehicle type actually exists
        if(vehicleType == null)
            throw new RARException("A Vehicle type with this name does not exist: " + vehicleTypeStr);
            
        // check if the vehicle type actually exists
        if(rentalDuration <= 0)
            throw new RARException("Rental Duration is not positive " + rentalDuration);
        
        // create the reservation
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
          reservationTime = format.parse(pickupTime);
        } catch (parseException e1){
          e1.printStackTrace();
        }
        reservation = objectLayer.createReservation(vehicleType, rentalLocation, customer, reservationTime, rentalDuration);
        objectLayer.storeReservation(reservation);

        return reservation.getId();
    }
}
