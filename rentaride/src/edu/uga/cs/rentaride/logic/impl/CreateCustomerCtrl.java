package edu.uga.cs.rentaride.logic.impl;

import java.util.List;
import java.util.Date;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class CreateCustomerCtrl {
    
    private ObjectLayer objectLayer = null;
    
    public CreateCustomerCtrl( ObjectLayer objectModel )
    {
        this.objectLayer = objectModel;
    }
    
    public long createCustomer( String firstName, String lastName, String userName, String emailAddress, String password, String licenseState, String licenseNumber, String residenceAddress, String cardNumber, Date cardExpiration  )
            throws RARException
    { 
        Customer 	            customer  = null;
        Customer              modelCustomer = null;
        List<Customer>    customers = null;

        modelCustomer = objectLayer.createCustomer();
        modelCustomer.setUserName( userName );
        customers = objectLayer.findCustomer( modelCustomer );
        if (customers.size() > 0)
		customer = customers.get(0);

	if(customer != null)
            throw new RARException( "A customer with this name already exists: " + userName );

        Date createDate = new Date("12-08-2015");
        Date membershipExpiration = new Date("06-08-2016");
        
        rentalLocation = objectLayer.createCustomer( String firstName, String lastName, String userName, String emailAddress, String password, Date createDate, Date membershipExpiration, String licenseState, 
            String licenseNumber, String residenceAddress, String cardNumber, Date cardExpiration );
        objectLayer.storeCustomer( customer );

        return customer.getId();
    }
}
