package edu.uga.cs.rentaride.entity.impl;


import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.persistence.impl.Persistent;
import edu.uga.cs.rentaride.entity.*;

/** This class represents a rental location in the Rent-A-Ride system.
 *
 */
public class RentalLocationImpl
    extends Persistent
	implements RentalLocation
{
	private String name;
	private String address;
	private int capacity;
	private List<Reservation> reservationList;
	private List<Vehicle> vehicleList;
	private Persistent p;
	
	/** Constructor for RentalLocation without parameters 
     * 
     */
	public RentalLocationImpl()	{
		super(-1);
		name = null;
		address = null;
		capacity = '\0';		
	}
	/** Constructor for rentalLocation with parameters
     * @param name of location
	 * @param address of location
	 * @param capacty of location
     */
	public RentalLocationImpl (String name, String address, int capacity){
		super(-1);
		this.name = name;
		this.address = address;
		this.capacity = capacity;
	}
	
    /** Return the name of this rental location
     * @return the name of this rental location
     */
    public String getName(){
		return name;
	}
    
    /** Set the name of this rental location
     * @param name the new name of this rental location
     * @throws RARException in case name is non-unique or null
     */
    public void setName( String name ) throws RARException{
		this.name = name;
	}
    
    /** Return the address of this rental location
     * @return the address of this rental location
     */
    public String getAddress(){
		return address;
	}
    
    /** Set the address of this rental location
     * @param address the new address for this rental location
     */
    public void setAddress( String address ){
		this.address = address;
	}
    
    /** Return the capacity of this rental location
     * @return the capacity of this rental location
     */
    public int getCapacity(){
		return capacity;
	}
    
    /** Set the capacity of this rental location
     * @param capacity the new capacity of this rental location
     * @throws RARException in case capacity is non-positive
     */
    public void setCapacity( int capacity ) throws RARException{
		this.capacity = capacity;
	}
    
    /** Get a list of reservations made for this rental location.
     * @return a List of reservations made for this rental location
     */
    public List<Reservation> getReservations(){
    	/*if(reservationList == null){
    		if(isPersistent()){
    			Reservation r = new ReservationImpl();
    			r.setRentalLocation(this); //was previously "setRental"
    			//reservationList = Persistent(r.getId()).restoreRental(r);
    		}
    	}*/
    	return reservationList;
    }
    
    // Not needed;  reservations for this location are added one-by-one by creating 
    // Reservation objects or changing existing ones for this rental location.
    // void setReservatios( List<Reservation> reservations );
    
    /** Get a list of vehicles located at this rental location.
     * @return a List of vehicles located at this rental location
     */
    public List<Vehicle> getVehicles(){
    	/*if(vehicleList == null){
    		if(isPersistent()){
    			Vehicle v = new VehicleImpl();
    			v.setRentalLocation(this);
    			vehicleList = getPersistenceLayer().restoreVehicle(v);
    		}
    	}*/
    	return vehicleList;
    }
    
    // Not needed;  vehicles located at this location are added one-by-one by creating
    // Vehicle objects or re-assigning existing ones to this rental location.
    // void setVehicles( List<Reservation> reservations );}

}
