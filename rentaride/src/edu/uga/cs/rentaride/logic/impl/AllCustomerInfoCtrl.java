package edu.uga.cs.rentaride.logic.impl;

import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class AllCustomerInfoCtrl {
    
    private ObjectLayer objectLayer = null;
    
    public void AllCustomerInfoCtrl( ObjectLayer objectModel )
    {
        this.objectLayer = objectModel;
    }

    public List<Customer> ViewCustomerInfo()  //DELETED STRING PARAMETER, MIGHT BE PROBLEMATIC IN FUNCTION
            throws RARException
    {
        List<Customer> 	    customers  = null;

        customers = objectLayer.findCustomer( null );

        return customers;
    }
}
