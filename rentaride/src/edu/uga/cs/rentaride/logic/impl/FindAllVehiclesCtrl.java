package edu.uga.cs.rentaride.logic.impl;

import java.util.List;
import java.util.LinkedList;

import edu.uga.uga.cs.RARException;
import edu.uga.uga.cs.entity.RentalLocation;
import edu.uga.uga.cs.object.ObjectLayer;



public class FindAllVehiclesCtrl {
    
    private ObjectLayer objectLayer = null;
    
    public FindAllVehiclesCtrl(ObjectLayer objectModel)
    {
        this.objectLayer = objectModel;
    }

    public List<RentalLocation> findAllVehicless()
            throws RARException
    {
        List<Vehicle> 	    vehicles  = null;
        
        // retrieve all Vehicle objects`
        //
        vehicles = objectLayer.findVehicle(null);

        return vehicles;
    }
}
