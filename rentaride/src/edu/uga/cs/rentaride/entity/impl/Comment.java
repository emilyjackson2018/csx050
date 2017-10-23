package edu.uga.cs.rentaride.entity.impl;
import java.util.Date;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.persistence.Persistable;

public class Comment
implements Comment

{
	private String text;
	private Date date;
	private Rental rental;
	private Customer customer;
	
	/** Constructor for comment without parameters 
     * 
     */
	public Comment ()	{
		text = null;
		date = null;
		rental = null;
		customer = null;
		
	}
	/** Constructor for comment with parameters
     * @param text comment
	 * @param date of rental
	 * @param rental number
     * @param customer information
     */
	public Comment (String text, Date date, Rental rental, Customer customer){
		this.text = text;
		this.date = date;
		this.rental = rental;
		this.customer = customer;
	}
/** Return the text of this comment.
     * @return the text of this comment.
     */
    public String getText(){
		return text;
	}
    
    /** Set the new text of this comment.
     * @param text a String which is a text of this comment
     */
    public void setText( String text ){
		this.text = text;
	}
    
    /** Return the date of this comment.
     * @return the date of this comment
     */
    public Date getDate(){
		return date;
	}
    
    /** Set the date of this comment.
     * @param date the new date of this comment
     */
    public void setDate( Date date ){
		this.date = date;
	}
    
    /** Return the Rental object this comment is linked to.
     * @return the Rental object for this comment.
     */
    public Rental getRental(){
		return rental;
	}
    
    /** Set the new Rental object linked to this comment.
     * @param rental the new Rental object this comment should be linked to.
     * @throws RARException in case the rental value is null
     */
    public void setRental( Rental rental ) throws RARException{
		this.rental = rental;
	}
    
    /** Returns the customer who commented on a rental.
     * @return the Customer who made this comment
     */
    public Customer getCustomer(){
		return customer;
	}
    
    /** Set the customer for this Comment.
     * It is a derived association, so there is no setter method for this value.
     * @param customer the customer who made the comment
     */
    // public void setCustomer( Customer customer );
}