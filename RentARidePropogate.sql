INSERT INTO Administrator
VALUES ('1');
INSERT INTO Administrator
VALUES ('2');

INSERT INTO Comments
VALUES ('This system rocks!', '2017-09-30', '1');
INSERT INTO Comments
VALUES ('This system sucks!', '2017-10-03', '2');

INSERT INTO Customer
VALUES ('1', '2020-05-20', 'GA', 'PZZ1203', '20305', '2018-11-08', 'ACTIVE');
INSERT INTO Customer
VALUES ('2', '2022-07-31', 'FL', 'U4EEA', '76032', '2017-12-31', 'TERMINATED');

INSERT INTO HourlyPrice
VALUES ('500', '4000');
INSERT INTO HourlyPrice
VALUES ('1000', '8000');

INSERT INTO RentARideParams
VALUES ('100', '200');
INSERT INTO RentARideParams
VALUES ('50', '200');

INSERT INTO Rental
VALUES ('1', '2016-10-07 14:00:00', '2016-10-11 14:00:00', '0', '0', '1', '1', '1', 'OK', 'Car');
INSERT INTO Rental
VALUES ('2', '2017-05-04 14:00:00', '2016-05-15 14:01:00', '1', '200', '2', '2', '2', 'Needs maintenance', 'Car');

INSERT INTO RentalLocation
VALUES ('1', 'Atlanta', '502 Sandy Springs Circle Atlanta, GA 30328', '100');
INSERT INTO RentalLocation
VALUES ('2', 'Jacksonville', '1042 Kernan Blvd Jacksonville, FL 32258', '250');

INSERT INTO Reservation
VALUES ('1', '2016-10-07 14:00:00', '120', 'FALSE', '1', '1', '1', '1');
INSERT INTO Reservation
VALUES ('2', '2017-05-04 14:00:00', '36', 'FALSE', '2', '2', '2', '2');

INSERT INTO UserStatus
VALUES ('TERMINATED');
INSERT INTO UserStatus
VALUES ('ACTIVE');

INSERT INTO Users
VALUES ('1', 'Emily', 'Jackson', 'EmmsChristine', 'b@dp@$$w0rd', 'emmschristine@outlook.com', '1042 Black Oak Drive Athens, GA 30606', '2017-10-04 18:33:00');
INSERT INTO Users
VALUES ('2', 'Joe', 'Smith', 'JSmith', 'yo0o0o0o0o', 'jsmith@gmail.com', '100 Kernan Blvd Jacksonville, FL 32258', '2015-08-26 12:02:00');

INSERT INTO Vehicle
VALUES ('1', '1', 'Honda', 'Civic', '2014', '47000', 'PZZ1203', '2017-07-01 14:56:00', 'INLOCATION', 'GOOD', 'Car');
INSERT INTO Vehicle
VALUES ('2', '2', 'Volkswagen', 'Beetle', '1998', '80000', 'SWGWGN', '2017-10-04 10:04:00', 'INRENTAL', 'NEEDSMAINTENANCE', 'Car');

INSERT INTO VehicleCondition
VALUES ('GOOD');
INSERT INTO VehicleCondition
VALUES ('NEEDSMAINTENANCE');

INSERT INTO VehicleStatus
VALUES ('INRENTAL');
INSERT INTO VehicleStatus
VALUES ('INLOCATION');

INSERT INTO VehicleType
VALUES ('Honda Civic', '230');
INSERT INTO VehicleType
VALUES ('Volkswagen Beetle', '50');

