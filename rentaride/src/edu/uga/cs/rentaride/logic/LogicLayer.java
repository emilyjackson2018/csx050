package edu.uga.cs.rentaride.logic;


import java.util.List;
import java.util.Date;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.entity.UserStatus;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;

import edu.uga.cs.rentaride.object.ObjectLayer;

public interface LogicLayer
{
    public List<RentalLocation>  findAllRentalLocations() throws RARException;
    public long                  createRentalLocation(String name, String addr, int capacity) throws RARException;
    
    public long                  createCustomer(Date membershipExpiration, String licenseState, String licenseNumber, 
                                 String residenceAddress, String cardNumber, Date cardExpiration, String fName, String lName,
                                 String uName, String email, String password, Date createDate) throws RARException;
    /*
    public List<Customer>        AllCustomerInfo() throws RARException;
    public List<Customer>        ViewCustomerInfo() throws RARException;
    
    public List<Vehicle>         FindAllVehicles() throws RARException;*/
    
    public long                  createVehicleType(String type) throws RARException;
                                                                                                //had to add everything from here on out
    public void logout(String ssid) throws RARException;

    public String login(Session session, String userName, String password) 
            throws RARException;
            
}

