package edu.uga.cs.rentaride.entity;

/** This class represents an Administrator user.  It has no additional attributes, and all are inherited from User.
 *
 */
public interface Administrator 
    extends User
{
	/** Constructor for administrator without parameters 
     * 
     */
	public Administrator();
	public Administrator(String firstName, String lastName, String userName, String email, String password, Date createDate, 
	String address, UserStatus userStatus);
}
