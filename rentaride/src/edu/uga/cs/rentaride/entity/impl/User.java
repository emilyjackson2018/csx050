package edu.uga.cs.rentaride.entity.impl;

import java.util.Date;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.persistence.Persistable;


/** This is the base class of all known users of the Rent-A-Ride system.
 *
 */
public class User
    extends Persistable
	implements User
{
	
	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String password;
	private Date createDate;
	private String address;
	private UserStatus userStatus;
	
	
	/** Constructor for user without parameters 
     * 
     */
	public User ()	{
		super(-1);
		firstName = null;
		lastName = null;
		userName = null;
		email = null;
		password = null;
		createDate = null;
		address = null;
		userStatus = null;
		
	}
	/** Constructor for user with parameters
     * @param firstName of user
	 * @param lastName of user
	 * @param userName of user
     * @param email of user
	 * @param password of user
	 * @param createDate of user
	 * @param address of user
	 * @param userStatus status of user
     */
	public User (String firstName, String lastName, String userName, String email, String password, Date createDate, String address, UserStatus userStatus){
		super(-1);
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.createDate = createDate;
		this.address = address;
		this.userStatus = userStatus;
	}
	
    /** Return the user's first name.
     * @return the user's first name
     */
    public String getFirstName(){
		return firstName;
	}
    
    /** Set the user's first name.
     * @param firstName the new first name
     */
    public void setFirstName( String firstName ){
		this.firstName = firstName;
	}
    
    /** Return the user's last name.
     * @return the user's last name
     */
    public String getLastName(){
		return lastName;
	}
    
    /** Set the user's last name.
     * @param lastName the new last name
     */
    public void setLastName( String lastName ){
		this.lastName = lastName;
	}
    
    /** Return the user's user (login) name.
     * @return the user's user (login) name
     */
    public String getUserName(){
		return userName;
	}
    
    /** Set the user's user (login) name.
     * @param userName the new user (login) name
     * @throws RARException in case userName is non-unique
     */
    public void setUserName( String userName ) throws RARException{
		this.userName = userName;
	}
    
    /** Return the user's email address.
     * @return the user's email address
     */
    public String getEmail(){
		return email;
	}
    
    /** Set the user's email address.
     * @param email the new email address
     */
    public void setEmail( String email ){
		this.email = email;
	}
    
    /** Return the user's password.
     * @return the user's password
     */
    public String getPassword(){
		return password;
	}
    
    /** Set the user's password.
     * @param password the new password
     */
    public void setPassword( String password ){
		this.password = password;
	}
    
    /** Return the user's creation date.
     * @return the user's creation date
     */
    public Date getCreatedDate(){
		return createDate;
	}
    
    /** Set the user's creation date.
     * @param createDate the new creation date
     */
    public void setCreateDate( Date createDate ){
		this.createDate = createDate;
	}
    
    /** Return the residence address of this user.
     * @return the address of this user's residence
     */
    public String getAddress(){
		return address;
	}
    
    /** Set the new residence address of this user.
     * @param address the new residence address of this user
     */
    public void setAddress( String address ){
		this.address = address;
	}
    
    /** Return the current status of this user (ACTIVE, REMOVED, or TERMINATED)
     * @return the current status of this user
     */
    public UserStatus getUserStatus(){
		return userStatus;
	}
    
    /** Set the current status of this user (must be ACTIVE, CANCELLED, or TERMINATED)
     * @param userStatus the new status of this user (must be ACTIVE, CANCELLED, or TERMINATED)
     */
    public void setUserStatus( UserStatus userStatus ){
		this.userStatus = userStatus;
	}
}
