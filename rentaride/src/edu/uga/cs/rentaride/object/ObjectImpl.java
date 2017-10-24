package edu.uga.cs.rentaride.object.impl;

import java.util.Date;
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
import edu.uga.cs.rentaride.entity.UserStatus;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleCondition;
import edu.uga.cs.rentaride.entity.VehicleStatus;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.entity.impl.AdministratorImpl;
import edu.uga.cs.rentaride.entity.impl.CommentImpl;
import edu.uga.cs.rentaride.entity.impl.CustomerImpl;
import edu.uga.cs.rentaride.entity.impl.HourlyPriceImpl;
import edu.uga.cs.rentaride.entity.impl.RentARideConfigImpl;
import edu.uga.cs.rentaride.entity.impl.RentalImpl;
import edu.uga.cs.rentaride.entity.impl.RentalLocationImpl;
import edu.uga.cs.rentaride.entity.impl.ReservationImpl;
import edu.uga.cs.rentaride.entity.impl.VehicleImpl;
import edu.uga.cs.rentaride.entity.impl.VehicleTypeImpl;
import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.persistence.PersistenceLayer;
import edu.uga.cs.rentaride.persistence.impl.PersistenceLayerImpl;

public class ObjectLayerImpl 
implements ObjectLayer
{
	PersistenceLayer persistence = null;

	public ObjectLayerImpl() {
		this.persistence = null;
		System.out.println("ObjectLayerImpl.ObjectLayerImpl(): initialized");
	}

	public ObjectLayerImpl(PersistenceLayer persistence) {
		this.persistence = persistence;
		System.out.println("ObjectLayerImpl.ObjectLayerImpl(persistence): initialized");
	}

	public void setPersistence(PersistenceLayer persistence) {
		this.persistence = persistence;
		System.out.println("ObjectLayerImpl.setPersistence(persistence): initialized");
	}

	public Adminsitrator createAdministrator(String firstName, String lastName, String userName, String emailAddress, String password, Date createdDate) {
		return new AdministratorImpl(firstName, lastName, userName, emailAddress, password, createdDate, UserStatus.ACTIVE, "admin");

		public Administrator createAdministrator()
		{
			return new AdministratorImpl(null, null, null, null, null, null, UserStatus.ACTIVE,"admin");
		} 

		public Iterator<Administrator> findAdministrator(Administrator modelAdministrator) throws RARException {
			return persistence.restoreAdministrator(modelAdministrator);
		}

		public void storeAdministrator(Administrator administrator) throws RARException {
			persistence.storeAdministrator(administrator);
		}

		public void deleteAdministrator(Administrator administrator) throws RARException {
			persistence.deleteAdministrator(administrator);
		}

		public Customer createCustomer(String firstName, String lastName, String emailAddress, String password, Date createdDate, Date membershipExpiration, String licenseState, String licenseNumber, String residenceAddress, String cardNumber, Date cardExpiration) {
			CustomerImpl customer = new CustomerImpl(firstName, lastName, userName, emailAddress, password, createdDate, membershipExpiration, licenseState, licenseNumber, residenceAddress, cardNumber, cardExpiration);
			return new CustomerImpl(membershipExpiration, licenseState, licenseNumber, residenceAddress, cardNumber, cardExpiration, firstName, lastName, userName, emailAddress, password, createDate, UserStatus.ACTIVE);
		}

		public Customer createCustomer() {
			return new CustomerImpl(null, null, null, null, null, null, null, null, null, null, null, UserStatus.ACTIVE);

			public Iterator<Customer> findCustomer(Customer modelCustomer) throws RARException {
				return persistence.restoreCustomer(modelCustomer);
			}

			public void storeCustomer(Customer customer) throws RARException {
				persistence.storeCustomer(customer);
			}

			public void deleteCustomer(Customer customer) throws RARException {
				persistence.deleteCustomer(customer);
			}

			public RentalLocation createRentalLocation(String name, String address, int capacity) throws RARException {
				return new RentalLocationImpl(name, address, capacity);
			}

			public RentalLocation createRentalLocation() {
				return new RentalLocationImpl(null, null, 0);
			}

			public Iteration<RentalLocation> findRentalLocation(RentalLocation modelRentalLocation) throws RARException {
				return persistence.restoreRentalLocation(modelRentalLocation);
			}

			public void storeRentalLocation (RentalLocation rentalLocation) throws RARException {
				persistence.storeRentalLocation(rentalLocation);
			}

			public void deleteRentalLocation(RentalLocation rentalLocation) throws RARException {
				persistence.deleteRentalLocation(rentalLocation);
			}

			public Reservation createReservation( VehicleType vehicleType, RentalLocation rentalLocation, Customer customer,
					Date pickupTime, int rentalDuration) throws RARException {
				return new ReservationImpl( pickupTime, rentalDuration, customer, vehicleType, rentalLocation);
			}

			public Reservation createReservation()  {
				return new ReservationImpl(null, 0, null, null, null); 
			}

			public Iterator<Reservation> findReservation(Reservation modelReservation) throws RARException {
				return persistence.restoreReservation (modelReservation);
			}

			public void storeReservation(Reservation reservation) throws RARException {
				persistence.storeReservation(reservation);
			}

			public void deleteReservation(Reservation reservation) throws RARException {
				persistence.deleteReservation(reservation);
			}

			public Rental createRental(Reservation reservation, Customer customer, Vehicle vehicle, Date pickupTime) throws RARException {
				return new RentalImpl(pickupTime, null, reservation, vehicle, customer, 0);
			}

			public Rental createRental() {
				return new RentalImpl(null, null, null, null, null, 0);
			}

			public Iterator<Rental> findRental(Rental modelRental) throws RARException {
				return persistence.restoreRental(modelRental);
			}

			public void storeRental(Rental rental) throws RARException {
				persistence.storeRental(rental);
			}

			public void deleteRental(Rental rental) throws RARException {
				persistence.deleteRental(rental);
			}

			public VehicleType createVehicleType(String type) {
				return new VehicleTypeImpl(type);
			}

			public VehicleType createVehicleType() {
				return new VehicleTypeImpl(null);
			}

			public Iterator<VehicleType> findVehicleType(VehicleType modelVehicleType) throws RARException {
				return persistence.restoreVehicleType(modelVehicleType);
			}

			public void storeVehicleType(VehicleType vehicleType) throws RARException {
				persistence.storeVehicleType(vehicleType);
			}

			public void deleteVehicleType(VehicleType vehicleType) throws RARException {
				persistence.deleteVehicleType(vehicleType);
			}

			public Vehicle createVehicle(VehicleType vehicleType, String make, String model, int year, String registrationTag, int mileage, Date lastServiced, 
					RentalLocation rentalLocation, VehicleCondition vehicleCondition, VehicleStatus vehicleStatus) throws RARException {
				return new VehicleImpl(vehicleType, make, model, year, registrationTag, mileage, lastServiced, 
						rentalLocation, vehicleCondition, vehicleStatus);
			}

			public Vehicle createVehicle() {
				return new VehicleImpl(null, null, null, 0 ,null ,0 , null , null ,null , null);
			}

			public Iterator<Vehicle> findVehicle(Vehicle modelVehicle) throws RARException {
				return persistence.restoreVehicle(modelVehicle);
			}

			public void storeVehicle(Vehicle vehicle) throws RARException {
				persistence.storeVehicle(vehicle);
			}

			public void deleteVehicle(Vehicle vehicle) throws RARException {
				persistence.deleteVehicle(vehicle);
			}

			public Comment createComment(String comment, Rental rental, Customer customer) throws RARException {
				return new CommentImpl(comment, customer, rental);
			}

			public Comment createComment() {
				return new CommentImpl(null, null, null);
			}

			public Iterator<Comment> findComment(Comment modelComment) throws RARException
			{
				return persistence.restoreComment(modelComment);
			}

			public void storeComment(Comment comment) throws RARException {
				persistence.storeComment(comment);
			}

			public void deleteComment(Comment comment) throws RARException {
				persistence.deleteComment(comment);
			}

			public HourlyPrice createHourlyPrice(int minHrs, int maxHrs, int price, VehicleType vehicleType) throws RARException {
				return new HourlyPriceImpl(minHrs, maxHrs, price, vehicleType);
			}

			public HourlyPrice createHourlyPrice() {
				return new HourlyPriceImpl(0, 0, 0, null);
			}

			public Iterator<HourlyPrice> findHourlyPrice(HourlyPrice modelHourlyPrice) throws RARException {
				return persistence.restoreHourlyPrice(modelHourlyPrice);
			}

			public void storeHourlyPrice(HourlyPrice hourlyPrice) throws RARException {
				persistence.storeHourlyPrice(hourlyPrice);
			}

			public void deleteHourlyPrice(HourlyPrice hourlyPrice) throws RARException {
				persistence.deleteHourlyPrice(hourlyPrice);
			}

			public RentARideConfig findRentARideConfig() throws RARException {
				return persistence.restoreRentARideConfig(); 

			}

			public void storeRentARideCfg(RentARideConfig rentARideCfg) throws RARException {
				persistence.storeRentARideConfig(rentARideCfg);
			}

			public void createCustomerReservation(Customer customer, Reservation reservation) throws RARException {
				persistence.storeCustomerReservation(customer, reservation);
			}

			public Customer restoreCustomerReservation(Reservation reservation) throws RARException {
				return persistence.restoreCustomerReservation(reservation);
			}

			public Iterator<Reservation> restoreCustomerReservation(Customer customer) throws RARException {
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

			public Iterator<Reservation> restoreReservationRentalLocation(RentalLocation rentalLocation) throws RARException {
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

			public Iterator<Vehicle> restoreVehicleRentalLocation(RentalLocation rentalLocation) throws RARException {
				return persistence.restoreVehicletRentalLocation(rentalLocation);
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

			public Iterator<Vehicle> restoreVehicleVehicleType(VehicleType vehicleType) throws RARException {
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

			public Iterator<Reservation> restoreReservationVehicleType(VehicleType vehicleType) throws RARException {
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

			public Iterator<HourlyPrice> restoreVehicleTypeHourlyPrice(VehicleType vehicleType) throws RARException {
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

			public Iterator<Comment> restoreRentalComment(Rental rental) throws RARException {
				return persistence.restoreRentalComment(rental);
			}

			public void deleteRentalComment(Rental rental, Comment comment) throws RARException {
				persistence.deleteRentalComment(rental, comment);
			}

			public Customer restoreCustomerComment(Comment comment) throws RARException {
				return persistence.restoreCustomerComment(comment);
			}

			public Iterator<Comment> restoreCustomerComment(Customer customer) throws RARException {
				return persistence.restoreCustomerComment(customer);
			}

			public Customer restoreRentalCustomer(Rental rental) throws RARException {
				return persistence.restoreRentalCustomer(rental);
			}

			public Iterator<Rental> restoreRentalCustomer(Customer customer) throws RARException {
				return persistence.restoreRentalCustomer(customer);
			}

			@Override
				public RentARideConfig findRentARideCfg() {
					return null;
				}

			@Override
				public Iterator<Vehicle> restoreVehicleRentalLocation(RentalLocation rentalLocation) throws RARException {
					return null;
				}

		}


	}
