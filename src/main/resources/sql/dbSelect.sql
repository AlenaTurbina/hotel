Select * from Hotel.User_Role
Select * from Hotel.User_Status
Select * from Hotel.User
Select * from Hotel.Room_Type
Select * from Hotel.Class_Apartment 
Select * from Hotel.Room 
Select * from Hotel.Optional 
Select * from Hotel.Order_Status
Select * from Hotel.Order_Booking
Select * from Hotel.Order_Booking_With_Optional
Select * from Hotel.Invoice


SELECT 
Order_Booking.id, date, 
User.first_name, User.last_name, 
date_arrival, date_departure, quantity_persons,
Room.name as room_name, 
Room_Type.name as room_type, 
Class_Apartment.name as class_apartment,
Invoice.sum_total as sum,
Order_Status.name as order_status
FROM
Hotel.Order_Booking
INNER JOIN Hotel.User
ON Order_Booking.client = User.id
INNER JOIN Hotel.Room
ON Order_Booking.room = Room.id
INNER JOIN Hotel.Room_Type
ON Room.room_type = Room_Type.id
INNER JOIN Hotel.Class_Apartment
ON Room.class_apartment = Class_Apartment.id
INNER JOIN Hotel.Invoice
ON Order_Booking.id = Invoice.order_booking
INNER JOIN Hotel.Order_Status
ON Order_Booking.order_status = Order_Status.id
