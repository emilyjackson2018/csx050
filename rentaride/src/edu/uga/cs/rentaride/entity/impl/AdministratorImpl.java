package edu.uga.cs.rentaride.entity.impl;

import java.util.Date;

import edu.uga.cs.rentaride.entity.*;
import edu.uga.cs.rentaride.entity.impl.*;

public class AdministratorImpl
	extends UserImpl
	implements Administrator
{
	/** Constructor for administrator
     * 
     */
	public AdministratorImpl(){
		super();
	}
	
	public AdministratorImpl(String firstName, String lastName, String userName, String email, String password, Date createDate, 
	String address, UserStatus userStatus){
		super(firstName, lastName, userName, email, password, createDate, address, userStatus);

	}
	


}
