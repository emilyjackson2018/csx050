package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class VehicleType
    implements Iterator<VehicleType> {
    private ResultSet   rs = null;
    private ObjectLayer objectLayer = null;
    private boolean     more = false;
    public VehicleType(ResultSet rs, ObjectLayer objectLayer) throws RARException {
        this.rs = rs;
        this.objectLayer = objectLayer;
        try {
            more = rs.next();
        }
        catch(Exception e) {
            throw new RARException("VehicleType: Cannot create an iterator; root cause: " + e);
        }
    }

    public boolean hasNext() 
    { 
        return more; 
    }

    public VehicleType next() 
    {
    	long id;
    	String type;
    	VehicleType vehicleType = null;
        
        
        if(more) {

            try {
            	id = rs.getLong("vehicleTypeId");
                type = rs.getString("TypeName");
                more = rs.next();
            }
            catch(Exception e) {
                throw new NoSuchElementException("VehicleType: No next VehicleType object; root cause: " + e);
            }
            vehicleType = objectLayer.createVehicleType(type); 
            vehicleType.setId(id);
            return vehicleType;
        }
        else {
            throw new NoSuchElementException("VehicleType: No next VehicleType object");
        }
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }

}

