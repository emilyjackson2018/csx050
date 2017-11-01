package edu.uga.cs.rentaride.logic.impl;

import java.util.Date;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class CreateRentalLocationCtrl {
    
    private ObjectLayer objectLayer = null;
    
    public CreateRentalLocationCtrl(ObjectLayer objectModel)
    {
        this.objectLayer = objectModel;
    }
    
    public long createRentalLocation(String name, String addr, int capacity)
            throws RARException
    { 
        RentalLocation 	            rentalLocation  = null;
        RentalLocation              modelRentalLocation = null;
        List<RentalLocation>    rentalLocations = null;

        modelRentalLocation = objectLayer.createRentalLocation();
        modelRentalLocation.setName(name);
        rentalLocations = objectLayer.findRentalLocation(modelRentalLocation);
        if(rentalLocations.size() > 0)
            rentalLocation = rentalLocations.get(0);
	
	if (rentalLocation != null)
		throw new RARException("A rental location with this name already exists: " + name);

        rentalLocation = objectLayer.createRentalLocation(name, addr, capacity);
        objectLayer.storeRentalLocation(rentalLocation);

        return rentalLocation.getId();
    }
}
