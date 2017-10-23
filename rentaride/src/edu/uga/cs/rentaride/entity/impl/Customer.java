package edu.uga.cs.rentaride.entity.impl;

import java.util.Date;
import java.util.List;

import edu.uga.cs.rentaride.RARException;


/** This class represents information about a registered customer of Rent-A-Ride.
 *
 */
public class Customer 
    extends User
	implements Customer
{
	private Date memberUntil;
	private String licState;
	private String licNumber;
	private String ccNumber;
	private String ccExpiration;
	private List<Reservation> reservationList;
	private List<Comment> commentList;
	private List<Rental> rentalList;
	
	/** Customer contructor without parameters
     * 
     */
	public Customer(){
		super(-1);
		this.memberUntil = null;
		this.licState = null;
		this.licNumber = null;
		this.ccNumber = null;
		this.ccExpiration = null;
		this.reservationList = null;
		this.commentList = null;
		this.rentalList = null;
		
	}
	
	/** Customer contructor with parameters
	 * @param memberUntil membership expiration
	 * @param licState license state
	 * @param licNumber license number
	 * @param ccNumber credit card number
	 * @param ccExpiration credit card expiration
	 * @param reservationList list of reservations
	 * @param commentList list of comments
	 * @param rentalList list of rentals 
     */
	public Customer(Date memberUntil, String licState, String licNumber, String ccNumber, String ccExpiration, List<Reservation> reservationList,
	List<Comment> commentList, List<Rental> rentalList){
		super(-1);
		this.memberUntil = memberUntil;
		this.licState = licState;
		this.licNumber = licNumber;
		this.ccNumber = ccNumber;
		this.ccExpiration = ccExpiration;
		this.reservationList = reservationList;
		this.commentList = commentList;
		this.rentalList = rentalList;				
	}
    /** Return the expiration Date of this Customer's membership in Rent-A-Ride.
     * @return the membership expiration Date for this customer 
     */
    public Date getMemberUntil(){
		return memberUntil;
	}
    
    /** Set the expiration Date of this Customer's membership in Rent-A-Ride.
     * @param memberUntil the new expiration Date for this customer
     * @throws RARException in case the membership date is in the past
     */
    public void setMemberUntil( Date memberUntil ) throws RARException{
		this.memberUntil = memberUntil;
	}
    
    /** Return the license state for this customer.
     * @return the String representing the state of the customer's license
     */
    public String getLicenseState(){
		return licState;
	}
    
    /** Set the new license state for this customer.
     * @param state the new state of this customer's license
     */
    public void setLicenseState( String state ){
		licState = state;
	}
    
    /** Return the license number of this customer.
     * @return the license number of this customer
     */
    public String getLicenseNumber(){
		return licNumber;
	}
    
    /** Set the new license number of this customer.
     * @param licenseNumber the new license number of this customer
     */
    public void setLicenseNumber( String licenseNumber ){
		licNumber = licenseNumber;
	}
    
    /** Return the credit card number of this customer.
     * @return the credit card number of this customer
     */
    public String getCreditCardNumber(){
		return ccNumber;
	}
    
    /** Set the new credit card number of this customer.
     * @param cardNumber the new credit card number of this customer
     */
    public void setCreditCardNumber( String cardNumber ){
		ccNumber = cardNumber;
	}
    
    /** Return the expiration date of this customer's credit card.
     * @return the expiration date of this customer's credit card
     */
    public Date getCreditCardExpiration(){
		return ccExpiration;
	}
    
    /** Set the new expiration date of this customer's credit card.
     * @param cardExpiration the new expiration date of this customer's credit card
     */
    public void setCreditCardExpiration( Date cardExpiration){
		ccExpiration = cardExpiration;
	}
    
    /** Return a list of all reservations made by this Customer.
     * @return a list of all reservations made by this Customer
     */
    public List<Reservation> getReservations(){
		return reservationList;
	}
    
    /** Return a list of all comments made by this Customer.
     * @return a list of all comments made by this Customer
     */
    public List<Comment> getComments(){
		return commentList;
	}
    
    /** Return a list of all rentals made by this Customer.
     * @return a list of all rentals made by this Customer
     */
    public List<Rental> getRentals(){
		return rentalList;
	}
}
