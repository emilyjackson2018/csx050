package edu.uga.cs.rentaride.entity.impl;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.persistence.Persistable;

/** This class represents the configuration parameters of the Rent-A-Ride system.
 * Note that the prices are always expressed in Cents, not Dollars.
 * This is a singleton class.
 *
 */
public class RentARideParams 
    extends Persistable
	implements RentARideParams
{
	private int membershipPrice;
	private int lateFee;
	
	/** Constructor for RentARideParams without parameters 
     * 
     */
	public RentARideParams ()	{
		super(-1);
		membershipPrice = null;
		lateFee = null;
		
	}
	/** Constructor for RentARideParams with parameters
     * @param membershipPrice price of membership
	 * @param lateFee of rental
     */
	public RentARideParams (int membershipPrice, int lateFee){
		super(-1);
		this.membershipPrice = membershipPrice;
		this.lateFee = lateFee;
	}
	
    /** Return the current price of the Rent-A-Ride membership.
     * @return the Rent-A-Ride current price (in cents) of the Rent-A-Ride membership
     */
    public int getMembershipPrice(){
		return membershipPrice;
	}
    
    /** Set the price of the Rent-A-Ride membership.
     * @param membershipPrice the new price (in cents) of the Rent-A-Ride membership
     * @throws RARException in case membershipPrice is non-positive
     */
    public void setMembershipPrice( int membershipPrice ) throws RARException{
		this.membershipPrice = membershipPrice;
	}
    
    /** Return the current late fee of the Rent-A-Ride membership.
     * @return the Rent-A-Ride current late fee (in cents)
     */
    public int getLateFee(){
		return lateFee;
	}
    
    /** Set the late fee of the Rent-A-Ride system.
     * @param lateFee the new late fee (in cents)
     * @throws RARException in case membershipPrice is negative
     */
    public void setLateFee( int lateFee ) throws RARException{
		this.lateFee = lateFee;
	}
}
