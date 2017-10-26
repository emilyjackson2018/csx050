package edu.uga.cs.rentaride.test;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import edu.uga.cs.rentaride.*;
import edu.uga.cs.rentaride.entity.*;
import edu.uga.cs.rentaride.entity.impl.*;
import edu.uga.cs.rentaride.object.*;
import edu.uga.cs.rentaride.object.impl.*;
import edu.uga.cs.rentaride.persistence.*;
import edu.uga.cs.rentaride.persistence.impl.*;


public class RentARideTester
{
    public static void main(String[] args)
    {
         Connection conn = null;
         ObjectLayer objectLayer = null;
         PersistenceLayer persistence = null;
		 
		 Administrator admin1 = null;
		 Administrator admin2 = null;
		 RentalLocation location1 = null;
		 RentalLocation location2 = null;
		 VehicleType type1 = null;
		 VehicleType type2 = null;
		 HourlyPrice price1 = null;
		 HourlyPrice price2 = null;
		 HourlyPrice price3 = null;
		 HourlyPrice price4 = null;
		 Vehicle v1 = null;
		 Vehicle v2 = null;
		 Vehicle v3 = null;
		 Vehicle v4 = null;
		 Customer customer1 = null;
		 Customer customer2 = null;
		 Reservation r1 = null;
		 Reservation r2 = null;
		 Reservation r3 = null;
		 Reservation r4 = null;
		 Rental rental1 = null;
		 Rental rental2 = null;
		 Comment comment1 = null;
		 Comment comment2 = null;
		 List<HourlyPrice> priceList = null;
		 List<HourlyPrice> priceList2 = null;
		 List<Vehicle> vehicleList = null;
		 List<Vehicle> vehicleList2 = null;
		 List<Reservation> reservationList = null;
		 List<Reservation> reservationList2 = null;
		 List<Rental> rentalList = null;
 		 List<Comment> commentList = null;
		 
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
         objectLayer = new ObjectImpl();
         // obtain a reference to Persistence module and connect it to the ObjectModel        
         persistence = new PersistenceLayerImpl( conn, objectLayer ); 
         // connect the ObjectModel module to the Persistence module
         ((ObjectImpl) objectLayer).setPersistence( persistence );  
		 
		 try{
			 //admins
			 admin1 = objectLayer.createAdministrator("Joe", "Smith", "jsmith", "jsmith@gmail.com", "pass",  new Date(), "123 ABC Street", UserStatus.ACTIVE);
			 persistence.storeAdministrator(admin1);
			 admin2 = objectLayer.createAdministrator("Jane","Smith", "janesmith", "janesmith@gmail.com", "pass", new Date(), "123 ABC Street", UserStatus.ACTIVE);
			 persistence.storeAdministrator(admin2);
			 //locations
			 location1 = objectLayer.createRentalLocation("Publix", "123 Barnett Shoals", 25);
			 persistence.storeRentalLocation(location1);
			 location2 = objectLayer.createRentalLocation("Kroger", "456 Barnett Shoals", 40);
			 persistence.storeRentalLocation(location2);
			 //vehicle types with hourly prices
			 type1 = objectLayer.createVehicleType("sedan", priceList, vehicleList, reservationList);
			 persistence.storeVehicleType(type1);
			 price1 = objectLayer.createHourlyPrice(2,10, type1);
			 persistence.storeHourlyPrice(price1);
			 price2 = objectLayer.createHourlyPrice(3,12, type1);
			 persistence.storeHourlyPrice(price2);
			 type2 = objectLayer.createVehicleType("coup", priceList2, vehicleList2, reservationList2);
			 persistence.storeVehicleType(type1);
			 price3 = objectLayer.createHourlyPrice(4,15, type2);
			 persistence.storeHourlyPrice(price3);
			 price4 = objectLayer.createHourlyPrice(3,18, type2);
			 persistence.storeHourlyPrice(price4);
			 //vehicles
			 v1 = objectLayer.createVehicle("Hyundai", "Genesis", 2013, "1234567", 10000, new Date(), VehicleStatus.INRENTAL, VehicleCondition.GOOD, type2, location1, rentalList);
			 persistence.storeVehicle(v1);
			 v2 = objectLayer.createVehicle("Honda", "Civic", 2015, "7654321", 5000, new Date(), VehicleStatus.INLOCATION, VehicleCondition.GOOD, type1, location2, rentalList);
			 persistence.storeVehicle(v2);
			 v3 = objectLayer.createVehicle("Ford", "Focus", 2016, "1234999", 7000, new Date(), VehicleStatus.INRENTAL, VehicleCondition.GOOD, type1, location1, rentalList);
			 persistence.storeVehicle(v3);
			 v4 = objectLayer.createVehicle("Ford", "Mustang", 2012, "8884321", 25000, new Date(), VehicleStatus.INRENTAL, VehicleCondition.NEEDSMAINTENANCE, type2, location2, rentalList);
			 persistence.storeVehicle(v4);
			 //customers
			 customer1 = objectLayer.createCustomer(new Date(), "Ross", "Geller", "rossg", new Date(), reservationList, commentList, rentalList);
			 persistence.storeCustomer(customer1);
			 customer2 = objectLayer.createCustomer(new Date(), "Monica", "Geller", "monicag", new Date(), reservationList2, commentList, rentalList);
			 persistence.storeCustomer(customer2);
			 //reservations
			 //Date pickupTime, int length, Customer customer, VehicleType vehicleType, RentalLocation rentalLocation, Rental rental
			 r1 = objectLayer.createReservation(new Date(), 2, customer1, type1, location1, rental1);
			 persistence.storeReservation(r1);
			 r2 = objectLayer.createReservation(new Date(), 1, customer1, type2, location2, rental2);
			 persistence.storeReservation(r2);
			 r3 = objectLayer.createReservation(new Date(), 1, customer2, type2, location1, rental1);
			 persistence.storeReservation(r3);
			 r4 = objectLayer.createReservation(new Date(), 2, customer2, type1, location2, rental2);
			 persistence.storeReservation(r4);
			 //rentals
			 //Date pickupTime, Date returnTime, boolean late, int charges, Reservation reservation, Vehicle vehicle, Customer customer, CommentImpl comment
			 rental1 = objectLayer.createRental(new Date(), new Date(), true, 100, r1, v1, customer1, comment1);
			 persistence.storeRental(rental1);
			 rental2 = objectLayer.createRental(new Date(), new Date(), false, 0, r2, v4, customer2, comment2);
			 persistence.storeRental(rental2);
			 //comments
			 comment1 = objectLayer.createComment("This vehicle was nice", new Date(), rental1, customer1);
			 persistence.storeComment(comment1);
			 comment2 = objectLayer.createComment("This vehicle needs help it was not good", new Date(), rental2, customer2);
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
			 /*persistence.deleteCustomer(customer1);
			 objectLayer.deleteCustomer(customer1);
			 persistence.deleteCustomer(customer2);
			 objectLayer.deleteCustomer(customer2);*/
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
			 /*persistence.deleteLocation(location1);
			 objectLayer.deleteLocation(location1);
			 persistence.deleteLocation(location2);
			 objectLayer.deleteLocation(location2);*/
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
