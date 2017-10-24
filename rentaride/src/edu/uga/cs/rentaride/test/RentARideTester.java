package edu.uga.cs.rentaride.test;

import java.sql.Connection;
import java.util.Date;

import edu.uga.cs.rentaride.*
/*
import edu.uga.cs.rentaride.entity.*;
import edu.uga.cs.rentaride.object.*;
import edu.uga.cs.rentaride.persistence.*;
*/

public class RentARideTester
{
    public static void main(String[] args)
    {
         Connection conn = null;
         ObjectLayer objectLayer = null;
         PersistenceLayer persistence = null;
		 
		 Administrator admin1;
		 Administrator admin2;
		 RentalLocation location1;
		 RentalLocation location2;
		 VehicleType type1;
		 VehicleType type2;
		 HourlyPrice price1;
		 HourlyPrice price2;
		 HourlyPrice price3;
		 HourlyPrice price4;
		 Vehicle v1;
		 Vehicle v2;
		 Vehicle v3;
		 Vehicle v4;
		 Customer customer1;
		 Customer customer2;
		 Reservation r1;
		 Reservation r2;
		 Reservation r3;
		 Reservation r4;
		 Rental rental1;
		 Rental rental2;
		 Comment comment1;
		 Comment comment2;
		 
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
		 
		 try{
			 //admins
			 admin1 = objectLayer.createAdministrator("Joe", "Smith", "jsmith", "pass", "jsmith@gmail.com", "123 ABC Street", new Date());
			 persistence.storeAdministrator(admin1);
			 admin2 = objectLayer.createAdministrator("Jane","Smith", "janesmith", "pass", "janesmith@gmail.com", "123 ABC Street", new Date());
			 persistence.storeAdministrator(admin2);
			 //locations
			 location1 = objectLayer.createRentalLocation("Publix", "123 Barnett Shoals", 25);
			 persistence.storeRentalLocation(location1);
			 location2 = objectLayer.createRentalLocation("Kroger", "456 Barnett Shoals", 40);
			 persistence.storeRentalLocation(location2);
			 //vehicle types with hourly prices
			 type1 = objectLayer.createVehicleType("sedan");
			 persistence.storeVehicleType(type1);
			 price1 = objectLayer.setHourlyPrice(2,10, "sedan");
			 persistence.storeHourlyPrice(price1);
			 price2 = objectLayer.setHourlyPrice(3,12, "sedan");
			 persistence.storeHourlyPrice(price2);
			 type1 = objectLayer.createVehicleType("coup");
			 persistence.storeVehicleType(type1);
			 price3 = objectLayer.setHourlyPrice(4,15, "coup");
			 persistence.storeHourlyPrice(price3);
			 price4 = objectLayer.setHourlyPrice(3,18, "coup");
			 persistence.storeHourlyPrice(price4);
			 //vehicles
			 v1 = objectLayer.createVehicle("Hyundai", "Genesis", 2013, "1234567", 10000, new Date(), type2, location1, 'GOOD', 'INLOCATION');
			 persistence.storeVehicle(v1);
			 v2 = objectLayer.createVehicle("Honda", "Civic", 2015, "7654321", 5000, new Date(), type1, location2, 'GOOD', 'INLOCATION');
			 persistence.storeVehicle(v2);
			 v3 = objectLayer.createVehicle("Ford", "Focus", 2016, "1234999", 7000, new Date(), type1, location1, 'GOOD', 'INRENTAL');
			 persistence.storeVehicle(v3);
			 v4 = objectLayer.createVehicle("Ford", "Mustang", 2012, "8884321", 25000, new Date(), type2, location2, 'NEEDSMAINTENANCE', 'INRENTAL');
			 persistence.storeVehicle(v4);
			 //customers
			 customer1 = objectLayer.createCustomer("Ross", "Geller", "rossg", "dinosaurs", "ross@gmail.com", "454 1st Street", new Date(),
			 new Date(), "New York", "123789", "123-123-123-123" new Date());
			 persistence.storeCustomer(customer1);
			 customer2 = objectLayer.createCustomer("Monica", "Geller", "monicag", "chef", "ross@gmail.com", "460 1st Street", new Date(),
			 new Date(), "New York", "123987", "456-456-456-456" new Date());
			 persistence.storeCustomer(customer2);
			 //reservations
			 r1 = objectLayer.createReservation(new Date(), 2, type1, location1, customer1);
			 persistence.storeReservation(r1);
			 r2 = objectLayer.createReservation(new Date(), 1, type2, location2, customer1);
			 persistence.storeReservation(r2);
			 r3 = objectLayer.createReservation(new Date(), 1, type2, location1, customer2);
			 persistence.storeReservation(r3);
			 r4 = objectLayer.createReservation(new Date(), 2, type1, location2, customer2);
			 persistence.storeReservation(r4);
			 //rentals
			 rental1 = objectLayer.createRental(new Date(), r1, v3);
			 persistence.storeRental(rental1);
			 rental2 = objectLayer.createRental(new Date(), r2, v4);
			 persistence.storeRental(rental2);
			 //comments
			 comment1 = objectLayer.createComment("This vehicle was nice", new Date(), rental1);
			 persistence.storeComment(comment1);
			 comment2 = objectLayer.createComment("This vehicle needs help it was not good", new Date(), rental2);
			 persistence.storeComment(comment2);
			 
			 //delete the data
			 persistence.deleteComment(comment1);
			 objectLayer.deleteComment(comment1);
			 persistence.deleteComment(comment2);
			 objectLayer.deleteComment(comment2);
			 persistence.deleteRental(rental1);
			 objectLayer.deleteRental(rental1);
			 persistence.deleteRental(rental2);
			 objectLayer.deleteRental(rental2);
			 persistence.deleteReservation(r1);
			 objectLayer.deleteReservation(r1);
			 persistence.deleteReservation(r2);
			 objectLayer.deleteReservation(r2);
			 persistence.deleteReservation(r3);
			 objectLayer.deleteReservation(r3);
			 persistence.deleteReservation(r4);
			 objectLayer.deleteReservation(r4);
			 persistence.deleteCustomer(customer1);
			 objectLayer.deleteCustomer(customer1);
			 persistence.deleteCustomer(customer2);
			 objectLayer.deleteCustomer(customer2);
			 persistence.deleteVehicle(v1);
			 objectLayer.deleteVehicle(v1);
			 persistence.deleteVehicle(v2);
			 objectLayer.deleteVehicle(v2);
			 persistence.deleteVehicle(v3);
			 objectLayer.deleteVehicle(v3);
			 persistence.deleteVehicle(v4);
			 objectLayer.deleteVehicle(v4);
			 persistence.deleteHourlyPrice(price1);
			 objectLayer.deleteHourlyPrice(price1);
			 persistence.deleteHourlyPrice(price2);
			 objectLayer.deleteHourlyPrice(price2);
			 persistence.deleteHourlyPrice(price3);
			 objectLayer.deleteHourlyPrice(price3);
			 persistence.deleteHourlyPrice(price4);
			 objectLayer.deleteHourlyPrice(price4);
			 persistence.deleteVehicleType(type1);
			 objectLayer.deleteVehicleType(type1);
			 persistence.deleteVehicleType(type2);
			 objectLayer.deleteVehicleType(type2);
			 persistence.deleteLocation(location1);
			 objectLayer.deleteLocation(location1);
			 persistence.deleteLocation(location2);
			 objectLayer.deleteLocation(location2);
			 persistence.deleteAdministrator(admin1);
			 objectLayer.deleteAdministrator(admin1);
			 persistence.deleteAdministrator(admin2);
			 objectLayer.deleteAdministrator(admin2);
			 
		 }
		 catch (RARException re)
		 {
			 System.err.println( "Exception: " + re );
             re.printStackTrace();
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
}