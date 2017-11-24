package edu.uga.cs.rentaride.logic.impl;

import java.util.List;
import java.util.LinkedList;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.object.ObjectLayer;



public class FindAllVehiclesCtrl {
    
    private ObjectLayer objectLayer = null;
    
    public FindAllVehiclesCtrl(ObjectLayer objectModel)
    {
        this.objectLayer = objectModel;
    }

    public List<Vehicle> findAllVehicles() 		//changed return type here, might be wrong
            throws RARException
    {
        List<Vehicle> 	    vehicles  = null;
        
        // retrieve all Vehicle objects`
        //
        vehicles = objectLayer.findVehicle(null);

        return vehicles;
    }
}
