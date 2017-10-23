package edu.uga.cs.rentaride.entity.impl;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.persistence.Persistable;

/** This class represents an hourly price set for a specific time interval.
 * The price value is represented in Cents, not Dollars.
 *
 */
public class HourlyPrice
    extends Persistable
	implements HourlyPrice
{   
	private int maxHrs;
	private int price;
	private VehicleType vehicleType;
	
	/** Constructor for HourlyPrice without parameters 
     * 
     */
	public HourlyPrice(){
		super(-1);
		maxHrs = null;
		price = null;
		vehicleType = null;
	}
	
	/** Constructor for HourlyPrice with parameters
     * @param maxHrs maximum hours
	 * @param price of rental
	 * @param vehicleType type of vehicle
     */
	public HourlyPrice(int maxHrs, int price, VehicleType vehicleType){
		super(-1);
		if(vehicleType == null){
			System.out.println("Hourly price must be associated with a vehicle type.");
		}
		else{
			this.maxHrs = maxHrs;
			this.price = price;
			this.vehicleType = vehicleType;
			}
	}
	
    /** Return the maximum hours for this price setting.
     * @return the maximum hours for this price setting
     */
    public int getMaxHours(){
		return maxHrs;
	}
    
    /** Set the new maximum hours for this price setting.
     * @param maxHours the new maximum hours for this price setting
     * @throws RARException in case the maxHours value is not positive or is not greater than minHours
     */
    public void setMaxHours( int maxHours ) throws RARException{
		maxHrs = maxHours;
	}
    
    /** Return the price for this hourly price setting (value is in Cents).
     * @return the price of this hourly price setting
     */
    public int getPrice(){
		return price;
	}
    
    /** Set the new price for this hourly price setting (must be in Cents).
     * @param price the new price of this hourly setting (in Cents)
     * @throws RARException in case the price value is non-positive
     */
    public void setPrice( int price ) throws RARException{
		this.price = price;
	}
    
    /** Return the VehicleType of this price setting.
     * @return the VehicleType of this price setting
     */
    public VehicleType getVehicleType(){
		return vehicleType;
	}

    /** Set the new VehicleType of this price setting.
     * @param vehicleType the new VehicleType for this price setting
     * @throws RARException in case the vehicleType is null
     */
    public void setVehicleType( VehicleType vehicleType ) throws RARException{
		this.vehicleType = vehicleType;
	}
}
