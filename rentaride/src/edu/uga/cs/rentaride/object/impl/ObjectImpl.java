package edu.uga.cs.rentaride.object.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.*;
import edu.uga.cs.rentaride.entity.impl.*;
import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.persistence.PersistenceLayer;
import edu.uga.cs.rentaride.persistence.impl.PersistenceLayerImpl;

public class ObjectImpl 
implements ObjectLayer

{
	PersistenceLayer persistence = null;

	public ObjectImpl() {
		this.persistence = null;
		System.out.println("ObjectLayerImpl.ObjectLayerImpl(): initialized");
	}

	public ObjectImpl(PersistenceLayer persistence) {
		this.persistence = persistence;
		System.out.println("ObjectLayerImpl.ObjectLayerImpl(persistence): initialized");
	}

	public void setPersistence(PersistenceLayer persistence) {
		this.persistence = persistence;
		System.out.println("ObjectLayerImpl.setPersistence(persistence): initialized");
	}

	public Administrator createAdministrator(String firstName, String lastName, String userName, 
                                              String password, String email, String address, Date createDate) {
		return new AdministratorImpl(firstName, lastName, userName, password, email, address, createDate, null);
	}

		public Administrator createAdministrator()
		{
			return new AdministratorImpl(null, null, null, null, null, null, null, null);
		} 

		public List<Administrator> findAdministrator(Administrator modelAdministrator) throws RARException {
			return persistence.restoreAdministrator(modelAdministrator);
		}

		public void storeAdministrator(Administrator administrator) throws RARException {
			persistence.storeAdministrator(administrator);
		}

		public void deleteAdministrator(Administrator administrator) throws RARException {
			persistence.deleteAdministrator(administrator);
		}

		public Customer createCustomer(String firstName, String lastName, String userName, String password,
            String email, String address, Date createDate, Date membershipExpiration, String licenseState, 
            String licenseNumber, String cardNumber, Date cardExpiration) {
			CustomerImpl customer = new CustomerImpl(firstName, lastName, userName, password, email, address, createDate, membershipExpiration, licenseState, licenseNumber, cardNumber, cardExpiration, null);
			
			return customer;
		}
		

		public Customer createCustomer() {
			return new CustomerImpl();
		}

			public List<Customer> findCust(Customer customerID) throws RARException {
				return persistence.restoreCustomer(customerID);
			}

			public void storeCustomer(Customer customer) throws RARException {
				persistence.storeCustomer(customer);
			}

			/*public void deleteCustomer(Customer customer) throws RARException {
				persistence.deleteCustomer(customer);
			}*/

			public RentalLocation createRentalLocation(String name, String address, int capacity) throws RARException {
				return new RentalLocationImpl(name, address, capacity);
			}

			public RentalLocation createRentalLocation() {
				return new RentalLocationImpl(null, null, 0);
			}

			public List<RentalLocation> findRentalLocation(RentalLocation modelRentalLocation) throws RARException {
				return persistence.restoreRentalLocation(modelRentalLocation);
			}

			public void storeRentalLocation (RentalLocation rentalLocation) throws RARException {
				persistence.storeRentalLocation(rentalLocation);
			}

			public void deleteRentalLocation(RentalLocation rentalLocation) throws RARException {
				persistence.deleteRentalLocation(rentalLocation);
			}

			public Reservation createReservation(Date pickupTime, int rentalLength, VehicleType vehicleType, 
                                          RentalLocation rentalLocation, Customer customer) throws RARException {
				return new ReservationImpl(pickupTime, rentalLength, vehicleType, rentalLocation, customer);
			}

			
			public Reservation createReservation()  {
				return new ReservationImpl(); 
			}

			public List<Reservation> findReservation(Reservation modelReservation) throws RARException {
				return persistence.restoreReservation (modelReservation);
			}

			public void storeReservation(Reservation reservation) throws RARException {
				persistence.storeReservation(reservation);
			}

			public void deleteReservation(Reservation reservation) throws RARException {
				persistence.deleteReservation(reservation);
			}

			public Rental createRental(Date pickupTime, Reservation reservation, Vehicle vehicle) throws RARException {
				return new RentalImpl(pickupTime, reservation, vehicle);
			}

			public Rental createRental() {
				return new RentalImpl();
			}

			public List<Rental> findRental(Rental modelRental) throws RARException {
				return persistence.restoreRental(modelRental);
			}

			public void storeRental(Rental rental) throws RARException {
				persistence.storeRental(rental);
			}

			public void deleteRental(Rental rental) throws RARException {
				persistence.deleteRental(rental);
			}

			public VehicleType createVehicleType(String name) {
				return new VehicleTypeImpl(name);
			}

			public VehicleType createVehicleType() {
				return new VehicleTypeImpl();
			}

			public List<VehicleType> findVehicleType(VehicleType modelVehicleType) throws RARException {
				return persistence.restoreVehicleType(modelVehicleType);
			}

			public void storeVehicleType(VehicleType vehicleType) throws RARException {
				persistence.storeVehicleType(vehicleType);
			}

			public void deleteVehicleType(VehicleType vehicleType) throws RARException {
				persistence.deleteVehicleType(vehicleType);
			}

			public Vehicle createVehicle(String make, String model, int year, String registrationTag, int mileage, Date lastServiced,
                                  VehicleType vehicleType, RentalLocation rentalLocation, VehicleCondition vehicleCondition, 
                                  VehicleStatus vehicleStatus) throws RARException {
				return new VehicleImpl(make, model, year, registrationTag, mileage, lastServiced, vehicleType, rentalLocation, 
            vehicleCondition, vehicleStatus);
			}

			public Vehicle createVehicle() {
				return new VehicleImpl(  );
			}

			public List<Vehicle> findVehicle(Vehicle modelVehicle) throws RARException {
				return persistence.restoreVehicle(modelVehicle);
			}

			public void storeVehicle(Vehicle vehicle) throws RARException {
				persistence.storeVehicle(vehicle);
			}

			public void deleteVehicle(Vehicle vehicle) throws RARException {
				persistence.deleteVehicle(vehicle);
			}

			public CommentImpl createComment(String text, Date date, Rental rental) throws RARException {
				return new CommentImpl(text, date, rental);
			}

			public Comment createComment() {
				return new CommentImpl();
			}

			public List<Comment> findComment(Comment modelComment) throws RARException
			{
				return persistence.restoreComment(modelComment);
			}

			public void storeComment(Comment comment) throws RARException {
				persistence.storeComment(comment);
			}

			public void deleteComment(Comment comment) throws RARException {
				persistence.deleteComment(comment);
			}

			public HourlyPrice createHourlyPrice(int maxHrs, int price, VehicleType vehicleType) throws RARException {
				return new HourlyPriceImpl(maxHrs, price, vehicleType);
			}

			public HourlyPrice createHourlyPrice() {
				return new HourlyPriceImpl();
			}

			public List<HourlyPrice> findHourlyPrice(HourlyPrice modelHourlyPrice) throws RARException {
				return persistence.restoreHourlyPrice(modelHourlyPrice);
			}

			public void storeHourlyPrice(HourlyPrice hourlyPrice) throws RARException {
				persistence.storeHourlyPrice(hourlyPrice);
			}

			public void deleteHourlyPrice(HourlyPrice hourlyPrice) throws RARException {
				persistence.deleteHourlyPrice(hourlyPrice);
			}

			public RentARideParams findRentARideParams() throws RARException {
				return persistence.restoreRentARideConfig(); 

			}

			public void storeRentARideParams(RentARideParams rentARideParam) throws RARException {
				persistence.storeRentARideConfig(rentARideParam);
			}

			public void createCustomerReservation(Customer customer, Reservation reservation) throws RARException {
				persistence.storeCustomerReservation(customer, reservation);
			}

			public Customer restoreCustomerReservation(Reservation reservation) throws RARException {
				return persistence.restoreCustomerReservation(reservation);
			}

			public List<Reservation> restoreCustomerReservation(Customer customer) throws RARException {
				return persistence.restoreCustomerReservation(customer);
			}

			public void deleteCustomerReservation(Customer customer, Reservation reservation) throws RARException {
				persistence.deleteCustomerReservation(customer, reservation);
			}

			public void createReservationRentalLocation(Reservation reservation, RentalLocation rentalLocation) throws RARException {
				persistence.storeReservationRentalLocation(reservation, rentalLocation);
			}

			public RentalLocation restoreReservationRentalLocation(Reservation reservation) throws RARException {
				return persistence.restoreReservationRentalLocation(reservation);
			}

			public List<Reservation> restoreReservationRentalLocation(RentalLocation rentalLocation) throws RARException {
				return persistence.restoreReservationRentalLocation(rentalLocation);
			}

			public void deleteReservationRentalLocation(Reservation reservation, RentalLocation rentalLocation) throws RARException {
				persistence.deleteReservationRentalLocation(reservation, rentalLocation);
			}

			public void createVehicleRentalLocation(Vehicle vehicle, RentalLocation rentalLocation) throws RARException {
				persistence.storeVehicleRentalLocation(vehicle, rentalLocation);
			}

			public RentalLocation restoreVehicleRentalLocation(Vehicle vehicle) throws RARException {
				return persistence.restoreVehicleRentalLocation(vehicle);
			}

			public List<Vehicle> restoreVehicleRentalLocation(RentalLocation rentalLocation) throws RARException {
				return persistence.restoreVehicleRentalLocation(rentalLocation);
			}

			public void deleteVehicleRentalLocation(Vehicle vehicle, RentalLocation rentalLocation) throws RARException {
				persistence.deleteVehicleRentalLocation(vehicle, rentalLocation);
			}

			public void createVehicleVehicleType(Vehicle vehicle, VehicleType vehicleType) throws RARException {
				persistence.storeVehicleVehicleType(vehicle, vehicleType);
			}

			public VehicleType restoreVehicleVehicleType(Vehicle vehicle) throws RARException {
				return persistence.restoreVehicleVehicleType(vehicle);
			}

			public List<Vehicle> restoreVehicleVehicleType(VehicleType vehicleType) throws RARException {
				return persistence.restoreVehicleVehicleType(vehicleType);
			}

			public void deleteVehicleVehicleType(Vehicle vehicle, VehicleType vehicleType) throws RARException {
				persistence.deleteVehicleVehicleType(vehicle, vehicleType);
			}

			public void createReservationVehicleType(Reservation reservation, VehicleType vehicleType) throws RARException {
				persistence.storeReservationVehicleType(reservation, vehicleType);
			}

			public VehicleType restoreReservationVehicleType(Reservation reservation) throws RARException {
				return persistence.restoreReservationVehicleType(reservation);
			}

			public List<Reservation> restoreReservationVehicleType(VehicleType vehicleType) throws RARException {
				return persistence.restoreReservationVehicleType(vehicleType);
			}

			public void deleteReservationVehicleType(Reservation reservation, VehicleType vehicleType) throws RARException {
				persistence.deleteReservationVehicleType(reservation, vehicleType);
			}

			public void createVehicleTypeHourlyPrice(VehicleType vehicleType, HourlyPrice priceSetting) throws RARException {
				persistence.storeVehicleTypeHourlyPrice(vehicleType, priceSetting);
			}

			public VehicleType restoreVehicleTypeHourlyPrice(HourlyPrice priceSetting) throws RARException {
				return persistence.restoreVehicleTypeHourlyPrice(priceSetting);
			}

			public List<HourlyPrice> restoreVehicleTypeHourlyPrice(VehicleType vehicleType) throws RARException {
				return persistence.restoreVehicleTypeHourlyPrice(vehicleType);
			}

			public void deleteVehicleTypeHourlyPrice(VehicleType vehicleType, HourlyPrice priceSetting) throws RARException {
				persistence.deleteVehicleTypeHourlyPrice(vehicleType, priceSetting);
			}

			public void createRentalComment(Rental rental, Comment comment) throws RARException {
				persistence.storeRentalComment(rental, comment);
			}

			public Rental restoreRentalComment(Comment comment) throws RARException {
				return persistence.restoreRentalComment(comment);
			}

			public List<Comment> restoreRentalComment(Rental rental) throws RARException {
				return persistence.restoreRentalComment(rental);
			}

			public void deleteRentalComment(Rental rental, Comment comment) throws RARException {
				persistence.deleteRentalComment(rental, comment);
			}

			/*public void storeComment(Comment comment) throws RARException {
				persistence.storeComment(comment);
			}

			public List<Comment> restoreComment(Comment customer) throws RARException {
				return persistence.restoreComment(customer);
			}

			public Customer restoreRentalReservation(Rental rental) throws RARException {        GIBBERISH? CAN'T CONNECT TO ANY PERSISTENCELAYER FUNCTIONS
				return persistence.restoreRentalCustomer(rental);
			}

			public void restoreRentalCustomer(Customer customer) throws RARException {
				return persistence.restoreRentalCustomer(customer);
			}*/

			/*@Override
				public RentARideParams findRentARideParams() {
					return null;
				}*/

			@Override
			public RentARideParams createRentARideParams() {
				return new RentARideParamsImpl();
			}

			/*@Override
			public void storeRentARideParams(RentARideParams rentARideParams)
					throws RARException {
				persistence.storeRentARideParams(rentARideParams);
				
			}*/

			@Override
			public List<Customer> findCustomer(Customer modelCustomer)
					throws RARException {
				// TODO Auto-generated method stub
				return null;
			}

			/*@Override
			public Rental createRental(Date pickupTime, Date returnTime,
					boolean late, int charges, Reservation reservation,
					Vehicle vehicle, Customer customer, Comment comment1)
					throws RARException {
				// TODO Auto-generated method stub
				return null;
			}*/

			/*@Override
				public List<Vehicle> restoreRentalLocationVehicles(RentalLocation rentalLocation) throws RARException {
					return null;
				}*/

		}

