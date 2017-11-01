package edu.uga.cs.rentaride.logic.impl;

import java.sql.Connection;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.object.impl.ObjectLayerImpl;
import edu.uga.cs.rentaride.persistence.PersistenceLayer;
import edu.uga.cs.rentaride.persistence.impl.PersistenceLayerImpl;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;

public class LogicLayerImpl 
    implements LogicLayer
{
    private ObjectLayer objectLayer = null;

    public LogicLayerImpl(Connection conn)
    {
        objectLayer = new ObjectLayerImpl();
        PersistenceLayer persistenceLayer = new PersistenceLayerImpl(conn, objectLayer);
        objectLayer.setPersistence(persistenceLayer);
        System.out.println("LogicLayerImpl.LogicLayerImpl(conn): initialized");
    }
    
    public LogicLayerImpl(ObjectLayer objectLayer)
    {
        this.objectLayer = objectLayer;
        System.out.println("LogicLayerImpl.LogicLayerImpl(objectLayer): initialized");
    }

    public List<RentalLocation> findAllRentalLocations() 
            throws RARException
    {
        FindAllRentalLocationsCtrl ctrlFindAllRentalLocations = new FindAllRentalLocationsCtrl(objectLayer);
        return ctrlFindAllRentalLocations.findAllRentalLocations();
    }
    
    public List<VehicleType> findAllVehicleTypes() 
            throws RARException
    {
        FindAllVehicleTypesCtrl ctrlFindAllVehicleTypes = new FindAllVehicleTypesCtrl(objectLayer);
        return ctrlFindAllVehicleTypes.findAllVehicleTypes();
    }
    
    public long createRentalLocation(String name, String addr, int capacity)
            throws RARException
    {
        CreateRentalLocationCtrl ctrlCreateRentalLocation = new CreateRentalLocationCtrl (objectLayer);
        return ctrlCreateRentalLocation.createRentalLocation(name, addr, capacity);
    }
    
     public long createVehicleType(String name)
            throws RARException
    {
        CreateVehicleTypeCtrl ctrlCreateVehicleType = new CreateVehicleTypeCtrl (objectLayer);
        return ctrlCreateVehicleType.createVehicleType(name);
    }

    public void logout(String ssid) throws RARException
    {
        SessionManager.logout(ssid);
    }

    public String login(Session session, String userName, String password) 
            throws RARException
    {
        LoginCtrl ctrlVerifyPerson = new LoginCtrl(objectLayer);
        return ctrlVerifyPerson.login(session, userName, password);
    }
}
