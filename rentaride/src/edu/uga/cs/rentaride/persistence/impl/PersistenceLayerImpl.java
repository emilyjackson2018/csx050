package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.entity.Comment;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.RentARideConfig;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.persistence.PersistenceLayer;
import edu.uga.cs.rentaride.persistence.*;
import edu.uga.cs.rentaride.persistence.impl.*;


public class PersistenceLayerImpl
    implements PersistenceLayer
{
    private AdministratorManager administratorManager = null;
    private CommentManager commentManager = null;
    private CustomerManager customerManager = null;
    private HourlyPriceManager hourlyPriceManager = null;
    private RentalManager rentalManager = null;
    private RentalLocationManager rentalLocationManager = null;
    private ReservationManager reservationManager = null;
    private VehicleManager vehicleManager = null;
    private VehicleTypeManager vehicleTypeManager = null;
    
    public PersistenceLayerImpl( Connection conn, ObjectLayer objectLayer )
    {
        administratorManager = new AdministratorManager( conn, objectLayer);
        commentManager = new CommentManager( conn, objectLayer);
        customerManager = new CustomerManager( conn, objectLayer);
        hourlyPriceManager = new HourlyPriceManager( conn, objectLayer);
        rentalManager = new RentalManager( conn, objectLayer);
        rentalLocationManager = new RentalLocationManager( conn, objectLayer);
        reservationManager = new ReservationManager( conn, objectLayer);
        vehicleManager = new VehicleManager( conn, objectLayer);
        vehicleTypeManager = new VehicleTypeManager( conn, objectLayer);
        System.out.println("PersistenceLayerImpl.PersistenceLayerImpl(conn,objectLayer): initialized");
    }
    
    public Iterator<Administrator> restoreAdministrator( Administrator modelAdministrator ) throws RARException {
        return administratorManager.restore( modelAdministrator );
    }
    
    public void storeAdministrator( Administrator administrator ) throws RARException {
        administratorManager.save( administrator );
    }
    
    public void deleteAdministrator( Administrator administrator ) throws RARException {
        administratorManager.delete( administrator );
    }
    
    public Iterator<Customer> restoreCustomer( Customer modelCustomer ) throws RARException {
        return customerManager.restore( modelCustomer );
    }
    
    public void storeCustomer( Customer customer ) throws RARException {
        customerManager.save( customer );
    }
    
    public Iterator<RentalLocation> restoreRentalLocation( RentalLocation modelRentalLocation ) throws RARException {
        return rentalLocationManager.restore( modelRentalLocation );
    }
    
    public void storeRentalLocation( RentalLocation rentalLocation ) throws RARException {
        rentalLocationManager.save( rentalLocation );
    }
    
    public void deleteRentalLocation( RentalLocation rentalLocation ) throws RARException {
        rentalLocationManager.delete( rentalLocation );
    }
    
    public Iterator<Reservation> restoreReservation( Reservation modelReservation ) throws RARException {
        return reservationManager.restore( modelReservation );
    }
    
    public void storeReservation( Reservation reservation ) throws RARException {
        reservationManager.save( reservation );
    }
    
    public void deleteReservation( Reservation reservation ) throws RARException {
        reservationManager.delete( reservation );
    }
    
    public Iterator<Rental> restoreRental( Rental modelRental ) throws RARException {
        return rentalManager.restore( modelRental );
    }
    
    public void storeRental( Rental rental ) throws RARException {
        rentalManager.save( rental );
    }
    
    public void deleteRental( Rental rental ) throws RARException {
        rentalManager.delete( rental );
    }
    
    public Iterator<VehicleType> restoreVehicleType( VehicleType modelVehicleType ) throws RARException {
        return vehicleTypeManager.restore( modelVehicleType );
    }

    public void storeVehicleType( VehicleType vehicleType ) throws RARException {
        vehicleTypeManager.save( vehicleType );
    }
    
    public void deleteVehicleType( VehicleType vehicleType ) throws RARException {
        vehicleTypeManager.delete( vehicleType );
    }
    
    public Iterator<Vehicle> restoreVehicle( Vehicle modelVehicle ) throws RARException {
        return vehicleManager.restore( modelVehicle );
    }
    
    public void storeVehicle( Vehicle vehicle ) throws RARException {
        vehicleManager.save( vehicle );
    }
    
    public void deleteVehicle( Vehicle vehicle ) throws RARException {
        vehicleManager.delete( vehicle );
    }
    
    public Iterator<Comment> restoreComment( Comment modelComment ) throws RARException {
        return commentManager.restore( modelComment );
    }
    
    public void storeComment( Comment comment ) throws RARException {
        commentManager.save( comment );
    }
    
    public void deleteComment( Comment comment ) throws RARException {
        commentManager.delete( comment );
    }
    
    public Iterator<HourlyPrice> restoreHourlyPrice( HourlyPrice modelHourlyPrice ) throws RARException {
        return hourlyPriceManager.restore( modelHourlyPrice );
    }
    
    public void storeHourlyPrice( HourlyPrice hourlyPrice ) throws RARException {
        hourlyPriceManager.save( hourlyPrice );
    }
    
    public void deleteHourlyPrice( HourlyPrice hourlyPrice ) throws RARException {
        hourlyPriceManager.delete( hourlyPrice );
    }

    public RentARideConfig restoreRentARideConfig() throws RARException {
        return rentARideConfigManager.restore( );
    }
    
    public void storeRentARideConfig( RentARideConfig rentARideConfig ) throws RARException {
        rentARideConfigManager.save( rentARideConfig );
    }
    
	public void storeCustomerReservation( Customer customer, Reservation reservation ) throws RARException {
        if (customer == null)
            throw new RARException("The customer is null");
        if (!customer.isPersistent())
            throw new RARException("The customer is not persistent");
        
        Rental rental = reservation.getRental();
        rental.setCustomer(customer);
        reservation.setCustomer( customer );
        reservationManager.save( reservation );
        rentalManager.save( rental );
    }

    public Customer restoreCustomerReservation( Reservation reservation ) throws RARException {
        return reservationManager.restoreCustomerReservation( reservation );
    }

    public Iterator<Reservation> restoreCustomerReservation( Customer customer ) throws RARException {
        return customerManager.restoreCustomerReservation( customer );
    }

    public void deleteCustomerReservation( Customer customer, Reservation reservation ) throws RARException {
        Rental rental = reservation.getRental();
        rental.setCustomer(null);
        reservation.setCustomer( null );
        reservationManager.save( reservation );
        rentalManager.save( rental );
    }
    
	public void storeReservationRentalLocation( Reservation reservation, RentalLocation rentalLocation ) throws RARException {
        if (rentalLocation == null)
            throw new RARException("The rental location is null");
        if (!rentalLocation.isPersistent())
            throw new RARException("The rental location is not persistent");
        
        reservation.setRentalLocation( rentalLocation );
        reservationManager.save( reservation );
    }

    public RentalLocation restoreReservationRentalLocation( Reservation reservation ) throws RARException {
        return reservationManager.restoreReservationRentalLocation( reservation );
    }

    public Iterator<Reservation> restoreReservationRentalLocation( RentalLocation rentalLocation ) throws RARException {
        return rentalLocationManager.restoreReservationRentalLocation( rentalLocation );
    }

    public void deleteReservationRentalLocation( Reservation reservation, RentalLocation rentalLocation ) throws RARException {
        reservation.setRentalLocation( null );
        reservationManager.save( reservation );
    }

    public void storeReservationVehicleType( Reservation reservation, VehicleType vehicleType ) throws RARException {
        if (vehicleType == null)
            throw new RARException("The vehicle type is null");
        if (!vehicleType.isPersistent())
            throw new RARException("The vehicle type is not persistent");
        
        reservation.setRentalLocation( vehicleType );
        reservationManager.save( reservation );
    }

    public VehicleType restoreReservationVehicleType( Reservation reservation ) throws RARException {
        return reservationManager.restoreReservationVehicleType(reservation );
    }

    public Iterator<Reservation> restoreReservationVehicleType( VehicleType vehicleType ) throws RARException {
        return vehicleManager.restoreReservationVehicleType( vehicleType );
    }

    public void deleteReservationVehicleType( Reservation reservation, VehicleType vehicleType ) throws RARException {
        reservation.setRentalLocation( null );
        reservationManager.save( reservation );
    }
    
    public void storeVehicleRentalLocation( Vehicle vehicle, RentalLocation rentalLocation ) throws RARException {
        if (rentalLocation == null)
            throw new RARException("The rental location is null");
        if (!rentalLocation.isPersistent())
            throw new RARException("The rental location is not persistent");
        
        vehicle.setRentalLocation( rentalLocation );
        vehicleManager.save( vehicle );
    }

    public RentalLocation restoreVehicleRentalLocation( Vehicle vehicle ) throws RARException {
        return vehicleManager.restoreVehicleRentalLocation( vehicle );
    }

    public Iterator<Vehicle> restoreVehicleRentalLocation( RentalLocation rentalLocation ) throws RARException {
         return rentalLocationManager.restoreVehicleRentalLocation( rentalLocation );
    }

    public void deleteVehicleRentalLocation( Vehicle vehicle, RentalLocation rentalLocation ) throws RARException {
        vehicle.setRentalLocation( null );
        vehicleManager.save( vehicle );
    }

    public void storeVehicleVehicleType( Vehicle vehicle, VehicleType vehicleType ) throws RARException {
        if (vehicleType == null)
            throw new RARException("The vehicleType is null");
        if (!vehicleType.isPersistent())
            throw new RARException("The vehicleType is not persistent");
        
        vehicle.setVehicleType( vehicleType );
        vehicleManager.save( vehicle );
    }

    public VehicleType restoreVehicleVehicleType( Vehicle vehicle ) throws RARException {
        return vehicleManager.restoreVehicleVehicleType( vehicle );
    }

    public Iterator<Vehicle> restoreVehicleVehicleType( VehicleType vehicleType ) throws RARException {
        return vehicleTypeManager.restoreVehicleVehicleType( vehicleType );
    }

    public void deleteVehicleVehicleType( Vehicle vehicle, VehicleType vehicleType ) throws RARException {
        vehicle.setVehicleType( null );
        vehicleManager.save( vehicle );
    }

    public void storeVehicleTypeHourlyPrice( VehicleType vehicleType, HourlyPrice hourlyPrice ) throws RARException {
        if (vehicleType == null)
            throw new RARException("The vehicleType is null");
        if (!vehicleType.isPersistent())
            throw new RARException("The vehicleType is not persistent");
        
        hourlyPrice.setVehicleType( vehicleType );
        hourlyPriceManager.save( hourlyprice );
    }

    public VehicleType restoreVehicleTypeHourlyPrice( HourlyPrice hourlyPrice ) throws RARException {
        return hourlyPriceManager.restoreVehicleTypeHourlyPrice( hourlyPrice );
    }

    public Iterator<HourlyPrice> restoreVehicleTypeHourlyPrice( VehicleType vehicleType ) throws RARException {
        return vehicleTypeManager.restoreVehicleTypeHourlyPrice( vehicleType );
    }
   public Iterator<HourlyPrice> restoreHourlyPrice( HourlyPrice modelHourlyPrice ) throws RARException {
        hourlyPrice.setVehicleType( null );
        hourlyPriceManager.save( hourlyprice );
    }

    public void storeRentalComment( Rental rental, Comment comment ) throws RARException {
        if (rental == null)
            throw new RARException("The rental is null");
        if (!rental.isPersistent())
            throw new RARException("The rental is not persistent");
        
        Customer customer = rental.getCustomer();
        comment.setCustomer(customer);
        comment.setRental( rental );
        commentManager.save( comment );
    }

    public Rental restoreRentalComment( Comment comment ) throws RARException {
        return commentManager.restoreRentalComment(comment);
    }

    public Iterator<Comment> restoreRentalComment( Rental rental ) throws RARException {
        return rentalManager.restoreRentalComment(rental);
    }

    public void deleteRentalComment( Rental rental, Comment comment ) throws RARException {
        comment.setCustomer(null);
        comment.setRental( null );
        commentManager.save( comment );
    }

    public Customer restoreCustomerComment( Comment comment ) throws RARException {
        return commentManager.restoreCustomerComment( comment ); 
    }

    public Iterator<Comment> restoreCustomerComment( Customer customer ) throws RARException {
        return customerManager.restoreCustomerComment( customer ); 
    }

    public Customer restoreRentalCustomer( Rental rental ) throws RARException {
        return rentalManager.restoreRentalCustomer( rental );
    }
    
    public Iterator<Rental> restoreRentalCustomer( Customer customer ) throws RARException {
        return customerManager.restoreRentalCustomer( customer );
    }

	@Override
	public Iterator<Vehicle> restoreVehicletRentalLocation(
			RentalLocation rentalLocation) throws RARException {
		return null;
	}
    
}

