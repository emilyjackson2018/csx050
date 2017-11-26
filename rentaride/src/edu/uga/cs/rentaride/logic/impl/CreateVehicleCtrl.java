package edu.uga.cs.rentaride.logic.impl;

import java.util.Date;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleCondition;
import edu.uga.cs.rentaride.entity.VehicleStatus;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class CreateVehicleCtrl {
    
    private ObjectLayer objectLayer = null;
    
    public CreateVehicleCtrl(ObjectLayer objectModel)
    {
        this.objectLayer = objectModel;
    }
    
    public long createVehicle(String registrationTag, Date lastService, String make, 
                               int mileage, String model, String rl_name, String rl_location, int rl_capacity, VehicleStatus status_str, 
                               String vehicleType_str, int vehicleYear, VehicleCondition vehicleCondition_str)
            throws RARException
    { 
        Vehicle 	       vehicle  = null;
        Vehicle            modelVehicle = null;
        List<Vehicle>  vehicles = null;
        RentalLocation  rentalLocation  = null;
        VehicleStatus          status          = null;
        VehicleType     vehicleType     = null;
        VehicleCondition vehicleCondition = null;

        // check if a vehicle with this name already exists (name is unique)
        modelVehicle = objectLayer.createVehicle();
        modelVehicle.setRegistrationTag(registrationTag);
        vehicles = objectLayer.findVehicle(modelVehicle);
        if(vehicles.size() > 0)
		vehicle = vehicles.get(0);

	if (vehicle != null)
            throw new RARException("A vehicle with this registration tag already exists: " + registrationTag);

        // create the vehicle
        rentalLocation = objectLayer.createRentalLocation("rl_str", "rl_location", rl_capacity);
        status = status_str;
        vehicleType = objectLayer.createVehicleType("vehicleType_str");
        
        vehicleCondition = vehicleCondition_str;
        
        vehicle = objectLayer.createVehicle(make, model, vehicleYear, registrationTag, mileage, lastService, 
			            vehicleType, rentalLocation, vehicleCondition, status);  	
        objectLayer.storeVehicle(vehicle);

        return vehicle.getId();
    }
}
