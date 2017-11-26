package edu.uga.cs.rentaride.logic.impl;

import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.object.ObjectLayer;


public class ViewCustomerInfoCtrl {
    
    private ObjectLayer objectLayer = null;
    
    public ViewCustomerInfoCtrl(ObjectLayer objectModel)
    {
        this.objectLayer = objectModel;
    }

    public List<Customer> ViewCustomerInfo(Customer modelCustomer)
            throws RARException
    {
        List<Customer> 	    customers  = null;

        // retrieve all Customer objects
        //
        customers = objectLayer.findCustomer(modelCustomer);
        return customers;
    }
}
