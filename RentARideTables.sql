/*drop tables if they exist*/
DROP TABLE IF EXISTS RentalLocation;
DROP TABLE IF EXISTS Vehicle;
DROP TABLE IF EXISTS VehicleType;
DROP TABLE IF EXISTS HourlyPrice;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Rental;
DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS Administrator;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Comments;
DROP TABLE IF EXISTS RentARideParams;
DROP TABLE IF EXISTS VehicleStatus;
DROP TABLE IF EXISTS VehicleCondition;
DROP TABLE IF EXISTS UserStatus;

/*create ENUM tables*/
CREATE TABLE VehicleStatus ( vs ENUM('INLOCATION', 'INRENTAL') );
CREATE TABLE VehicleCondition ( vc ENUM('GOOD', 'NEEDSMAINTENANCE') );
CREATE TABLE UserStatus ( us ENUM('ACTIVE', 'CANCELED', 'TERMINATED') );


/*create table for RentalLocation */
CREATE TABLE RentalLocation (
id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(255) NOT NULL,
address VARCHAR(255) NOT NULL,
capacity INT UNSIGNED NOT NULL
);

/*create table for HourlyPrice*/
CREATE TABLE HourlyPrice(
maxHrs INT UNSIGNED NOT NULL,
price INT UNSIGNED NOT NULL
);

/*create table for VehicleType*/
CREATE TABLE VehicleType (
name VARCHAR(255) NOT NULL,
price INT UNSIGNED NOT NULL,
FOREIGN KEY (price) REFERENCES HourlyPrice(price)
);

/*create table for Vehicle*/
CREATE TABLE Vehicle (
id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
locationID INT UNSIGNED NOT NULL,
make VARCHAR(255) NOT NULL,
model VARCHAR(255) NOT NULL,
vehicleYear INT UNSIGNED NOT NULL,
mileage INT UNSIGNED NOT NULL,
tag VARCHAR(255) NOT NULL,
lastServiced DATETIME NOT NULL,
status ENUM('INLOCATION', 'INRENTAL'),
vehicleCondition ENUM('GOOD', 'NEEDSMAINTENANCE'),
vehicleType VARCHAR(255) NOT NULL,
FOREIGN KEY (status) REFERENCES VehicleStatus(vs),
FOREIGN KEY (vehicleCondition) REFERENCES VehicleCondition(vc),
FOREIGN KEY (vehicleType) REFERENCES VehicleType(name),
FOREIGN KEY (locationID) REFERENCES RentalLocation(id)
);


/*create table for Users*/
CREATE TABLE Users(
id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
firstName VARCHAR(255) NOT NULL,
lastName VARCHAR(255) NOT NULL,
userName VARCHAR(255) NOT NULL,
password VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL,
address VARCHAR(255) NOT NULL,
createdDate DATETIME NOT NULL
);

/*create table for Rental*/
CREATE TABLE Rental(
id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
pickupTime DATETIME NOT NULL,
returnTime DATETIME NOT NULL,
late BIT NOT NULL,
charges INT UNSIGNED NOT NULL,
customerID INT UNSIGNED NOT NULL,
vehicleID INT UNSIGNED NOT NULL,
reservationID INT UNSIGNED NOT NULL,
text VARCHAR(255) NOT NULL,
vehicleType VARCHAR(255) NOT NULL,
FOREIGN KEY (vehicleType) REFERENCES VehicleType(name),
FOREIGN KEY (text) REFERENCES Comments(text),
FOREIGN KEY (pickupTime) REFERENCES Reservation(pickupTime),
FOREIGN KEY (reservationID) REFERENCES Reservation(id),
FOREIGN KEY (vehicleID) REFERENCES Vehicle(id),
FOREIGN KEY (customerID) REFERENCES Customer(id)
);

/*create table for Reservation*/
CREATE TABLE Reservation(
id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
pickupTime DATETIME NOT NULL,
reservationLength INT UNSIGNED NOT NULL,
canceled BIT NOT NULL,
customerID INT UNSIGNED NOT NULL,
rentalID INT UNSIGNED NOT NULL,
locationID VARCHAR(255) NOT NULL,
vehicleID INT UNSIGNED NOT NULL,
FOREIGN KEY (vehicleID) REFERENCES Vehicle(id),
FOREIGN KEY (customerID) REFERENCES Customer(id),
FOREIGN KEY (locationID) REFERENCES RentalLocation(id)
);

/*create table for Administrator*/
CREATE TABLE Administrator(
id INT UNSIGNED NOT NULL,
FOREIGN KEY (id) REFERENCES Users(id)
);

/*create table for Customer*/
CREATE TABLE Customer(
id INT UNSIGNED NOT NULL,
memberUntil DATETIME NOT NULL,
licState VARCHAR(255) NOT NULL,
licNumber VARCHAR(255) NOT NULL,
ccNumber VARCHAR(255) NOT NULL,
ccExpiration DATETIME NOT NULL,
status ENUM('ACTIVE', 'CANCELED', 'TERMINATED'),
FOREIGN KEY (id) REFERENCES Users(id),
FOREIGN KEY (status) REFERENCES UserStatus(us)
);

/*create table for Comments*/
CREATE TABLE Comments(
text VARCHAR(255) NOT NULL,
commentDate DATETIME NOT NULL,
customerID INT UNSIGNED NOT NULL,
FOREIGN KEY (customerID) REFERENCES Customer(id)
);

/*create table for RentARideParams*/
CREATE TABLE RentARideParams(
membershipPrice INT UNSIGNED NOT NULL,
lateFee INT UNSIGNED NOT NULL
);
