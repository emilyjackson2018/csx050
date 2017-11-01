package edu.uga.rentaride.logic.impl;

import java.util.List;

import edu.uga.rentaride.RARException;
import edu.uga.rentaride.entity.User;
import edu.uga.rentaride.entity.Customer;
import edu.uga.rentaride.entity.Administrator;
import edu.uga.rentaride.object.ObjectLayer;
import edu.uga.rentaride.session.Session;
import edu.uga.rentaride.session.SessionManager;

public class LoginCtrl
{ 
    private ObjectLayer objectLayer = null;
    
    public LoginCtrl(ObjectLayer objectLayer)
    {
        this.objectLayer = objectLayer;
    }
    
    public String login(Session session, String userName, String password)
            throws RARException
    {
        String ssid = null;
        
        Customer modelCustomer = objectLayer.createCustomer();
        modelCustomer.setUserName(userName);
        modelCustomer.setPassword(password);
        List<Customer> customers = objectLayer.findCustomer(modelCustomer);
        if(customers.size() > 0) {
            Customer customer = customers.get(0);
            session.setUser(customer);
            ssid = SessionManager.storeSession(session);
        }
        else
            Administrator modelAdministrator = objectLayer.createAdministrator();
            modelAdministrator.setUserName(userName);
            modelAdministrator.setPassword(password);
            List<Administrator> administrators = objectLayer.findAdministrator(modelAdministrator);
            if(administrators.size() > 0) {
                Administrator administrator = administrators.get(0);
                session.setUser(administrator);
                ssid = SessionManager.storeSession(session);
            }
            else
                throw new RARException("SessionManager.login: Invalid User Name or Password");
        }
        return ssid;
    }
}
