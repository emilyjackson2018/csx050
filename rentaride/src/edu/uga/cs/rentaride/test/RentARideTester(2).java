package edu.uga.cs.rentaride.test.object;

import java.util.List;
import java.sql.Connection;
import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.entity.Comment;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.RentARideParams;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleType;

import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.object.impl.ObjectLayerImpl;
import edu.uga.cs.rentaride.persistence.PersistenceLayer;
import edu.uga.cs.rentaride.persistence.impl.DbUtils;
import edu.uga.cs.rentaride.persistence.impl.PersistenceLayerImpl;

public class RentARideTester.java
{
	public static void main(String[] args) {
	
		Connection conn = null;
        ObjectLayer objectLayer = null;
        PersistenceLayer persistence = null;
		
		Administrator 		jeff;
		Administrator 		shelly;
		RentalLocation		vegas;
		RentalLocation		orlando;
		VehicleType			truck;
		VehicleType			car;
		HourlyPrice 		price1;
		HourlyPrice 		price2;
		HourlyPrice 		price3;
		HourlyPrice 		price4;
		Vehicle				ford;
		Vehicle 			jeep;
		Vehicle				dacia;
		Vehicle				camry;
		Customer			bill;
		Customer			jill;
		
		
		// get a database connection
         try {
             conn = DbUtils.connect();
         } 
         catch (Exception seq) {
             System.err.println( "WriteTest: Unable to obtain a database connection" );
         }
          
         if( conn == null ) {
             System.out.println( "WriteTest: failed to connect to the database" );
             return;
         }
          
         // obtain a reference to the ObjectModel module      
         objectLayer = new ObjectLayerImpl();
         // obtain a reference to Persistence module and connect it to the ObjectModel        
         persistence = new PersistenceLayerImpl( conn, objectLayer ); 
         // connect the ObjectModel module to the Persistence module
         objectLayer.setPersistence( persistence );
		 
		 try {
			
			 // admins
			 jeff = objectLayer.createAdminstrator("Jeff", "Geoff", "Dgef", "GEF777", "jeffrey777@jeff.com", new Date());
			 shelly = objectLayer.createAdminstrator("Shelly", "Shellz", "Sher", "SHER524", "sherwood@shell.com", new Date());
			 
			 persistence.storeAdministrator(jeff);
			 persistence.storeAdministrator(shelly);
			 
			 // locations
			 vegas = objectLayer.createRentalLocation("Casino", "Vegas Vegas 23rd", 10);
			 orlando = objectLayer.createRentalLocation("Disney", "524 Orange Lane", 20);
			 
			 persistence.storeRentalLocation(location1);
			 persistence.storeRentalLocation(location2);

			 
			 etc.
			 

			  // deletes
              
         }
         catch( RARException ce) {
             System.err.println( "Exception: " + ce );
             ce.printStackTrace();
         }
         catch( Exception e ) {
             e.printStackTrace();
         }
         finally {
             // close the connection
             try {
                 conn.close();
             }
             catch( Exception e ) {
                 System.err.println( "Exception: " + e );
             }
         }

	}