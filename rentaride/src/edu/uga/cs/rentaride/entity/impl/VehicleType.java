package edu.uga.cs.rentaride.entity.impl;


import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.persistence.Persistable;


/** This class represents possible vehicle types of all vehicles in the Rent-A-Ride system.
 * The types should be similar to sedan, pickup, luxury sedan, etc.
 *
 */
public class VehicleType
    extends Persistable
	implements VehicleType
{
	
	private String name;
	private List<HourlyPrice> priceList;
	private List<Vehicle> vehicleList;
	private List<Reservation> reservationList;
	
	/** Constructor for VehicleType without parameters 
     * 
     */
	public VehicleType ()	{
		super(-1);
		name = null;
		priceList = null;
		vehicleList = null;
		reservationList = null;
		
	}
	/** Constructor for VehicleType with parameters
     * @param name of vehicle type
	 * @param priceList of vehicle type
	 * @param vehicleList of vehicle type
	 * @param reservationList of vehicle type
     */
	public VehicleType (String name, List<HourlyPrice> priceList, List<Vehicle> vehicleList, List<Reservation> reservationList){
		super(-1);
		this.name = name;
		this.priceList = priceList;
		this.vehicleList = vehicleList;
		this.reservationList = reservationList;
	}
    /** Return the name of this vehicle type.
     * @return name of this vehicle type
     */
    public String getName(){
		return name;
	}
    
    /** Set the name of this vehicle type.
     * @param name the new name of this vehicle type
     * @throws RARException in case name is non-unique or null
     */
    public void setName( String name ) throws RARException{
		this.name = name;
	}
    
    /** Return a list of all hourly prices for this VehicleType.
     * @return a list of all hourly prices made by this VehicleType
     */
    public List<HourlyPrice> getHourlyPrices(){
		return priceList;
	}
    
    /** Return a list of all vehicles of this VehicleType.
     * @return a list of all vehicles of this VehicleType
     */
    public List<Vehicle> getVehicles(){
		return vehicleList;
	}
    
    /** Return a list of all reservations for this VehicleType.
     * @return a list of all reservations for this VehicleType
     */
    public List<Reservation> getReservations(){
		return reservationList;
	}
}
