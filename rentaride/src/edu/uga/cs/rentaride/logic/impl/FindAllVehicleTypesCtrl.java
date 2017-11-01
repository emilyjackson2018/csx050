package edu.uga.cs.rentaride.logic.impl;

import java.util.LinkedList;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class FindAllVehicleTypesCtrl {
    
    private ObjectLayer objectLayer = null;
    
    public FindAllVehicleTypesCtrl(ObjectLayer objectModel)
    {
        this.objectLayer = objectModel;
    }

    public List<VehicleType> findAllVehicleTypes()
            throws RARException
    {
        List<VehicleType> 	    vehicleTypes  = null;

        // retrieve all Rental Location objects
        //
        vehicleTypes = objectLayer.findVehicleType(null);

        return vehicleTypes;
    }
}
