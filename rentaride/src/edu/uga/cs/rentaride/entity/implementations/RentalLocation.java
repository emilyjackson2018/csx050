package edu.uga.cs.rentaride.entity.implementations;


import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.persistence.Persistable;

/** This class represents a rental location in the Rent-A-Ride system.
 *
 */
public class RentalLocation
    extends Persistable
	implements RentalLocation
{
	private String name;
	private String address;
	private int capacity;
	
	/** Constructor for RentalLocation without parameters 
     * 
     */
	public RentalLocation()	{
		name = null;
		address = null;
		capacity = null;		
	}
	/** Constructor for rentalLocation with parameters
     * @param name of location
	 * @param address of location
	 * @param capacty of location
     */
	public RentalLocation (String name, String address, int capacity){
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
}
