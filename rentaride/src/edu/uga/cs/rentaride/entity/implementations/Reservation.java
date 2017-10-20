package edu.uga.cs.rentaride.entity.implementations;

import java.util.Date;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.persistence.Persistable;


/** This class represents a reservation made by a Rent-A-Ride customer, for a vehicle type at a
 * specific rental location to be rented at a specific date and time.  The requested duration of
 * a rental must be expressed as a positive number of hours, which is less than or equal to 72.
 * Once the customer actually rents a vehicle, the reservation will involve a Rental object.
 *
 */
public class Reservation 
    extends Persistable
	implements Reservation
{
	private Date pickupTime;
	private int length;
	private Customer customer;
	private VehicleType vehicleType;
	private RentalLocation rentalLocation;
	private Rental rental;
	
	/** Constructor for Reservation without parameters 
     * 
     */
	public Reservation ()	{
		pickupTime = null;
		length = null;
		customer = null;
		vehicleType = null;
		rentalLocation = null;
		rental = null;
		
	}
	/** Constructor for Reservation with parameters
     * @param pickupTime time of pickup
	 * @param length of reservation
	 * @param customer who reserved
     * @param vehicleType of reservation
	 * @param rentalLocation of reservation
	 * @param rental information
     */
	public Reservation (Date pickupTime, int length, Customer customer, VehicleType vehicleType, RentalLocation rentalLocation, Rental rental){
		this.pickupTime = pickupTime;
		this.length = length;
		this.customer = customer;
		this.vehicleType = vehicleType;
		this.rentalLocation = rentalLocation;
		this.rental = rental;
	}
	
    /** Return the intended pickup time.
     * @return the pickup time for this reservation
     */
    public Date getPickupTime(){
		return pickupTime;
	}
    
    /** Set the intended pickup time.
     * @param pickupTime the new pickup time for this reservation
     * @throws RARException in case the pickup time is in the past
     */
    public void setPickupTime( Date pickupTime ) throws RARException{
		this.pickupTime = pickupTime;
	}
    
    /** Return the intended rental length (in hours).
     * @return the intended rental length (in hours)
     */
    public int getLength(){
		return length;
	}
    
    /** Set the intended rental length (in hours).
     * @param length the new intended rental length (in hours)
     * @throws RARException in case length time is illegal
     */
    public void setLength( int length ) throws RARException{
		this.length = length;
	}
    
    /** Return the customer who made this reservation.
     * @return the customer who made this reservation
     */
    public Customer getCustomer(){
		return customer;
	}
    
    /** Set the customer who made this reservation.
     * @param customer the customer who made this reservation
     * @throws RARException in case customer is null
     */
    public void setCustomer( Customer customer ) throws RARException{
		this.customer = customer;
	}
    
    /** Return the vehicle type of this reservation.
     * @return VehicleType for which this reservation was made
     */   
    public VehicleType getVehicleType(){
		return vehicleType;
	}
    
    /** Set the new vehicle type for this reservation.
     * @param vehicleType the new vehicle type for this reservation
     * @throws RARException in case vehicleType is null
     */ 
    public void setVehicleType( VehicleType vehicleType ) throws RARException{
		this.vehicleType = vehicleType;
	}
    
    /** Return the rental location for which this reservation was made.
     * @return the RentalLocation for which this reservation was made
     */
    public RentalLocation getRentalLocation(){
		return rentalLocation;
	}
    
    /** Set the new rental location for this reservation.
     * @param rentalLocation the new rental location for this reservation
     * @throws RARException in case rentalLocation is null
     */  
    public void setRentalLocation( RentalLocation rentalLocation ) throws RARException{
		this.rentalLocation = rentalLocation;
	}
    
    /** Return the rental that was based on this reservation.
     * @return the Rental based on this reservation
     */
    public Rental getRental(){
		return rental;
	}

    /** Set the rental that was based on this reservation.
     * @param rental the new Rental based on this reservation
     */
    public void setRental( Rental rental ){
		this.rental = rental;
	}
}
