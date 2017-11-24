package edu.uga.cs.rentaride.logic.impl;

import java.util.LinkedList;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.object.ObjectLayer;


public class FindAllRentalLocationsCtrl {
    
    private ObjectLayer objectLayer = null;
    
    public FindAllRentalLocationsCtrl(ObjectLayer objectModel)
    {
        this.objectLayer = objectModel;
    }

    public List<RentalLocation> findAllRentalLocations()
            throws RARException
    {
        List<RentalLocation> 	    rentalLocations  = null;
        
        rentalLocations = objectLayer.findRentalLocation(null);

        return rentalLocations;
    }
}
