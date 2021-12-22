Select * from Hotel2.Role
Select * from Hotel2.User_Status
Select * from Hotel2.User
Select * from Hotel2.Room_Type
Select * from Hotel2.Class_Apartment 
Select * from Hotel2.Room_Kind 
Select * from Hotel2.Room 
Select * from Hotel2.Optional 
Select * from Hotel2.Order_Status
Select * from Hotel2.Order_Booking
Select * from Hotel2.Order_Booking_With_Optional


SELECT 
Order_Booking.id, date, 
User.first_name, User.last_name, 
date_arrival, date_departure, quantity_persons,
Room.name as room_name, 
Room_Type.name as room_type, 
Class_Apartment.name as class_apartment,
Room_Price,
Sum_total,
Order_Status.name as order_status
FROM
Hotel2.Order_Booking
INNER JOIN Hotel2.User
ON Order_Booking.client = User.id
INNER JOIN Hotel2.Room
ON Order_Booking.room = Room.id
INNER JOIN Hotel2.Room_kind
ON Room.room_kind = Room_Kind.id
INNER JOIN Hotel2.Room_Type
ON Room_kind.room_type_id = Room_Type.id
INNER JOIN Hotel2.Class_Apartment
ON Room_kind.class_apartment_id = Class_Apartment.id
INNER JOIN Hotel2.Order_Status
ON Order_Booking.order_status = Order_Status.id



