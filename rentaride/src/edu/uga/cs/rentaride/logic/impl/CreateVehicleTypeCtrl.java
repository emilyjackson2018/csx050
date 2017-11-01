package edu.uga.cs.rentaride.logic.impl;

import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class CreateVehicleTypeCtrl {
    
    private ObjectLayer objectLayer = null;
    
    public CreateVehicleTypeCtrl(ObjectLayer objectModel)
    {
        this.objectLayer = objectModel;
    }
    
    public long createVehicleType(String type)
            throws RARException
    { 
        VehicleType	            vehicleType  = null;
        VehicleType              modelVehicleType = null;
        List<VehicleType>    vehicleTypes = null;

        // check if a Vehicle Type with this name already exists (name is unique)
        modelVehicleType = objectLayer.createVehicleType();
        modelVehicleType.setType(type);
        vehicleTypes = objectLayer.findVehicleType(modelVehicleType);
        if(vehicleTypes.size() > 0)
            vehicleType = vehicleTypes.get(0);

	if (vehicleType != null)
		throw new RARException("This Vehicle Type already exists: " + type);

        // create the vehicleType
        vehicleType = objectLayer.createVehicleType(type);
        objectLayer.storeVehicleType(vehicleType);

        return vehicleType.getId();
    }
}
