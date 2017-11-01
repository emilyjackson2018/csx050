package edu.uga.cs.rentaride.logic.impl;

import java.util.List;

import edu.uga.uga.cs.RARException;
import edu.uga.uga.cs.entity.Customer;
import edu.uga.uga.cs.object.ObjectLayer;

public class ViewCustomerInfoCtrl {
    
    private ObjectLayer objectLayer = null;
    
    public AllCustomerInfoCtrl( ObjectLayer objectModel )
    {
        this.objectLayer = objectModel;
    }

    public List<User> ViewCustomerInfo(String username)
            throws RARException
    {
        List<Customer> 	    customers  = null;

        customers = objectLayer.findCustomer( null );

        return customers;
    }
}
