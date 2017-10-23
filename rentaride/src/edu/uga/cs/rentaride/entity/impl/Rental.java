package edu.uga.cs.rentaride.entity.impl;

import java.util.Date;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.persistence.Persistable;

/** This class represent a rental event, involving a prior reservation, a vehicle being rented, and 
 * the customer.
 *
 */
public class Rental 
    extends Persistable
	implements Rental
{
	private Date pickupTime;
	private Date returnTime;
	private boolean late;
	private int charges;
	private Reservation reservation;
	private Vehicle vehicle;
	private Customer customer;
	private Comment comment;
	
	
	/** Constructor for rental without parameters 
     * 
     */
	public Rental (){
		super(-1);
		pickupTime = null;
		returnTime = null;
		late = false;
		charges = null;
		reservation = null;
		vehicle = null;
		customer = null;
		comment = null;
		
	}
	/** Constructor for rental with parameters
     * @param pickupTime for rental
	 * @param returnTime of rental
	 * @param late true if rental is late
     * @param charges applied to rental
	 * @param reservation of vehicle
	 * @param vehicle reserved
	 * @param customer reserving vehicle
	 * @param comment of customer on return
     */
	public Rental (Date pickupTime, Date returnTime, boolean late, int charges, Reservation reservation, Vehicle vehicle, Customer customer, Comment comment){
		super(-1);
		this.pickupTime = pickupTime;
		this.returnTime = returnTime;
		this.late = late;
		this.charges = charges;
		this.reservation = reservation;
		this.vehicle = vehicle;
		this.customer = customer;
		this.comment = comment;
	}
    /** Return the date when the vehicle in this rental was picked up.
     * @return the pickup date for this rental
     */
    public Date getPickupTime(){
		return pickupTime;
	}
    
    /** Set the date when the vehicle in this rental was picked up.
     * @param pickupTime the new pickup time of this rental
     */
    public void setPickupTime( Date pickupTime ){
		this.pickupTime = pickupTime;
	}
    
    /** Return the date when the vehicle in this rental was returned.
     * @return the date when the vehicle was returned
     */
    public Date getReturnTime(){
		return returnTime;
	}
    
    /** Set the date when the vehicle in this rental was returned.
     * @param returnTime the return time of this rental
     * @throws RARException in case the return time is not later than the pickup time
     */
    public void setReturnTime( Date returnTime ) throws RARException{
		this.returnTime = returnTime;
	}
    
    /** Return true if this rental was returned late; false otherwise.
     * It is a derived attribute, so no setter method is provided.
     * @return the late status of this rental
     */
    public boolean getLate(){
		return late;
	}
    
    /** Return the charges applied to this rental.
     * @return the charges applied to this rental
     */
    public int getCharges();
	{
		return charges;
	}
    
    /** Set the charges applied to this rental.
     * @param charges the new charges for this rental
     * @throws RARException in case charges is non-positive
     */
    public void setCharges( int charges ) throws RARException{
		this.charges = charges;
	}
    
    /** Return the reservation for this rental.
     * @return the Reservation object of this rental
     */
    public Reservation getReservation(){
		return reservation;
	}
    
    /** Set the reservation for this rental.
     * @param reservation the new Reservation object of this rental
     * @throws RARException in case reservation is null
     */
    public void setReservation( Reservation reservation ) throws RARException{
		this.reservation = reservation;
	}
    
    /** Return the vehicle for this rental.
     * @return the Vehicle object of this rental
     */
    public Vehicle getVehicle(){
		return vehicle;
	}
    
    /** Set the vehicle for this rental.
     * @param vehicle the new Vehicle for this rental
     * @throws RARException in case vehicle is null
     */
    public void setVehicle( Vehicle vehicle ) throws RARException{
		this.vehicle = vehicle;
	}
    
    /** Return the customer involved in this rental.
     * @return the Customer object of this rental
     */
    public Customer getCustomer(){
		return customer;
	}
    
    /** Set the customer involved in this rental.  
     * This is a derived association, so no setter is needed.
     * @param customer the new Customer for this rental
     */
    // public void setCustomer( Customer customer );
    
    /** Return the comment associated with this rental.
     * @return the Comment associated with this rental
     */
    public Comment getComment(){
		return comment;
	}
    
    /** Associate a comment with this rental.
     * @param comment the Comment to be associated with this rental
     */
    public void setComment( Comment comment ){
		this.comment = comment;
	}
}
