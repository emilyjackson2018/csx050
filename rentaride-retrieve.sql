# retrieve all administrators
select * from Administrator;

# retrieve all customers
select * from Customer;

# retrieve all rental locations and vehicles
select l.name, l.address, v.id, v.vehicleType
from RentalLocation l
        inner join Vehicle v on v.locationID = l.id;

#retrieve all rental locations and reservations there
select l.name, l.address, r.id, r.customerID, r.vehicleID
from RentalLocation l
        inner join Rental r on r.locationID = l.id;

#retrieve all vehicle types along with vehicles of listed type
select t.name, t.price, v.id, v.make, v.model
from VehicleType t
        inner join Vehicle v on v.vehicleType = t.name;

#retrieve all customers and their reservations
select c.id, r.id, r.rentalID, r.locationID, r.vehicleID
from Customer c
        inner join Reservation r on r.customerID = c.id;

#retrieve all customers and their rentals
select c.id, r.id, r.vehicleID, r.vehicleType, x.text
from Customer c
        inner join Rental r on r.customerID = c.id
        inner join Comments x on x.customerID = c.id;
