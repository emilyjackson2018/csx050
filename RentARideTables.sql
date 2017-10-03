--drop existing tables if they exist
DROP TABLE IF EXISTS RentalLocation;
DROP TABLE IF EXISTS Vehicle;
DROP TABLE IF EXISTS VehicleType;
DROP TABLE IF EXISTS HourlyPrice;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS Administrator;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Comments;
DROP TABLE IF EXISTS Rental;
DROP TABLE IF EXISTS RentARideParams;
DROP TABLE IF EXISTS VehicleStatus;
DROP TABLE IF EXISTS VehicleCondition;
DROP TABLE IF EXISTS UserStatus;

--create ENUM tables
CREATE TABLE VehicleStatus ( vs ENUM('INLOCATION', 'INRENTAL') );
CREATE TABLE VehicleCondition ( vc ENUM('GOOD', 'NEEDSMAINTENANCE') );
CREATE TABLE UserStatus ( us ENUM('ACTIVE', 'CANCELED', 'TERMINATED') );


--create table for RentalLocation 
CREATE TABLE RentalLocation (
name VARCHAR(255) NOT NULL;
address VARCHAR(255) NOT NULL;
capacity INT UNSIGNED NOT NULL;
);

--create table for Vehicle
CREATE TABLE Vehicle (
make VARCHAR(255) NOT NULL;
model VARCHAR(255) NOT NULL;
vehicleYear INT UNSIGNED NOT NULL;
mileage INT UNSIGNED NOT NULL;
tag VARCHAR(255) NOT NULL;
lastServiced DATETIME NOT NULL;
status AS SELECT vs AS s FROM VehicleStatus;
condition AS SELECT vc AS c FROM VehicleCondition;
);

--create table for VehicleType
CREATE TABLE VehicleType (
name VARCHAR(255) NOT NULL;
);

--create table for HourlyPrice
CREATE TABLE HourlyPrice(
maxHrs INT UNSIGNED NOT NULL;
price INT UNSIGNED NOT NULL;
);

--create table for Users
CREATE TABLE Users(
firstName VARCHAR(255) NOT NULL;
lastName VARCHAR(255) NOT NULL;
userName VARCHAR(255) NOT NULL;
password VARCHAR(255) NOT NULL;
email VARCHAR(255) NOT NULL;
address VARCHAR(255) NOT NULL;
createdDate DATETIME NOT NULL;
);

--create table for Reservation
CREATE TABLE Reservation(
pickup DATETIME NOT NULL;
length INT UNSIGNED NOT NULL;
canceled BIT NOT NULL;
);

--create table for Administrator
CREATE TABLE Administrator(
--no table elements listed
);

--create table for Customer
CREATE TABLE Customer(
memberUntil DATETIME NOT NULL;
licState VARCHAR(255) NOT NULL;
licNumber VARCHAR(255) NOT NULL;
ccNumber VARCHAR(255) NOT NULL;
ccExpiration DATETIME NOT NULL;
status AS SELECT us AS cs FROM UserStatus;
);

--create table for Comments
CREATE TABLE Comments(
text VARCHAR(255) NOT NULL;
commentDate DATETIME NOT NULL;
);

--create table for Rental
CREATE TABLE Rental(
pickupTime DATETIME NOT NULL;
returnTime DATETIME NOT NULL;
late BIT NOT NULL;
charges INT UNSIGNED NOT NULL;
);

--create table for RentARideParams
CREATE TABLE RentARideParams(
membershipPrice INT UNSIGNED NOT NULL;
lateFee INT UNSIGNED NOT NULL;
);
