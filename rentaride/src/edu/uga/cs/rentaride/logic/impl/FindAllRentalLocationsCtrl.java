package edu.uga.cs.rentaride.logic.impl;

import java.util.LinkedList;
import java.util.List;

import edu.uga.uga.cs.RARException;
import edu.uga.uga.cs.entity.RentalLocation;
import edu.uga.uga.cs.object.ObjectLayer;


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
