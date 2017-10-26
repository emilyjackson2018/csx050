package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;


public class HourlyPriceList
    implements Iterator<HourlyPrice>
{
    private ResultSet   rs = null;
    private ObjectLayer objectLayer = null;
    private boolean     more = false;
    public HourlyPriceList(ResultSet rs, ObjectLayer objectLayer) throws RARException {
        this.rs = rs;
        this.objectLayer = objectLayer;
        try {
            more = rs.next();
        }
        catch(Exception e) {
            throw new RARException("HourlyPrice: Cannot create an iterator; root cause: " + e);
        }
    }

    public boolean hasNext() { 
        return more; 
    }

    public HourlyPrice next() {
    	HourlyPrice hourlyPrice = null;
    	String name; 
    	List<HourlyPrice> priceList = null; 
    	List<Vehicle> vehicleList = null; 
    	List<Reservation> reservationList = null;
    	int maxHrs; 
    	int price; 
    	VehicleType vehicleType;
        
        if(more) {

            try {
                maxHrs = rs.getInt("maxHours");
                price = rs.getInt("price");
                //vehicleType = rs.getVehicleType("vehicleType");
                name = rs.getString("name");
                
                vehicleType = objectLayer.createVehicleType(name, priceList, vehicleList, reservationList); 
                more = rs.next();
            }
            catch(Exception e) {
                throw new NoSuchElementException("HourlyPrice: No next HourlyPrice object; root cause: " + e);
            }
            

            try {
            	hourlyPrice = objectLayer.createHourlyPrice(maxHrs, price, vehicleType);
            }
            catch(RARException ce) {
               ce.printStackTrace();
               System.out.println(ce);
            }

            return hourlyPrice;
        }
        else {
            throw new NoSuchElementException("HourlyPrice: No next HourlyPrice object");
        }
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

}

