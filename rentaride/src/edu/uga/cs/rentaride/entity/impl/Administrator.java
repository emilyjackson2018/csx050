package edu.uga.cs.rentaride.entity.impl;

public class Administrator
implements Administrator
extends User
{
	/** Constructor for administrator
     * 
     */
	public Administrator(){
		super();
	}
	
	public Administrator(String firstName, String lastName, String userName, String email, String password, Date createDate, 
	String address, UserStatus userStatus){
		super(firstName, lastName, userName, email, password, createDate, address, userStatus);
	}
	


}
