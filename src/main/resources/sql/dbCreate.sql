CREATE Database Hotel

CREATE TABLE Hotel.User_Role (
id bigint PRIMARY KEY Auto_increment,
name varchar (256) NOT NULL UNIQUE
)

CREATE TABLE Hotel.User_Status (
id bigint PRIMARY KEY Auto_increment,
name varchar (256) NOT NULL UNIQUE
)

CREATE TABLE Hotel.User (
id bigint PRIMARY KEY Auto_increment,
first_name varchar (256) NOT NULL,
last_name varchar (256) NOT NULL,
login varchar (100) NOT NULL UNIQUE,
password varchar (100) NOT NULL,
email varchar (256) NOT NUll UNIQUE,
phone_number varchar (100) NOT NULL,
document varchar (100) NOT NULL,
user_role bigint NOT NULL,
user_status bigint NOT NULL,
constraint fk_userRole FOREIGN KEY (user_role) references Hotel.User_Role(id),
constraint fk_userStatus FOREIGN KEY (user_status) references Hotel.User_Status(id)
)

CREATE TABLE Hotel.Room_Type (
id bigint PRIMARY KEY Auto_increment,
name varchar (256) NOT NULL UNIQUE,
quantity_places integer NOT NULL,
check (quantity_places>0)
)

CREATE TABLE Hotel.Class_Apartment (
id bigint PRIMARY KEY Auto_increment,
name varchar (256) NOT NULL UNIQUE,
place_price double NOT NULL,
check (place_price>0)
)

CREATE TABLE Hotel.Room (
id bigint PRIMARY KEY Auto_increment,
name varchar (10) NOT NULL UNIQUE,
room_price double NOT NULL,
class_apartment bigint NOT NULL,
room_type bigint NOT NULL,
constraint fk_classApartment FOREIGN KEY (class_apartment) references Hotel.Class_Apartment(id),
constraint fk_roomType FOREIGN KEY (room_type) references Hotel.Room_Type(id),
check (room_price>0)
)

CREATE TABLE Hotel.Optional (
id bigint PRIMARY KEY Auto_increment,
name varchar (256) NOT NULL UNIQUE,
optional_price double NOT NULL,
check (optional_price>0)
)

CREATE TABLE Hotel.Order_Status (
id bigint PRIMARY KEY Auto_increment,
name varchar (256) NOT NULL UNIQUE
)

CREATE TABLE Hotel.Order_Booking (
id bigint PRIMARY KEY Auto_increment,
date date NOT NULL,
date_arrival date NOT NULL,
date_departure date NOT NULL,
quantity_persons integer NOT NULL,
client bigint NOT NULL,
room bigint NOT NULL,
order_status bigint NOT NULL,
constraint fk_client FOREIGN KEY (client) references Hotel.User(id),
constraint fk_room FOREIGN KEY (room) references Hotel.Room(id),
constraint fk_orderStatus FOREIGN KEY (order_status) references Hotel.Order_Status(id),
check (date_departure>date_arrival),
check (date_arrival>=date)
)

CREATE TABLE Hotel.Order_Booking_With_Optional (
id bigint PRIMARY KEY auto_increment,
order_booking_id bigint NOT NULL,
optional_id bigint NOT NULL,
constraint fk_orderBokingID FOREIGN KEY (order_booking_id) references Hotel.Order_Booking(id),
constraint fk_optionalID FOREIGN KEY (optional_id) references Hotel.Optional(id),
UNIQUE (order_booking_id, optional_id)
)

CREATE TABLE Hotel.Invoice (
id bigint PRIMARY KEY Auto_increment,
sum_total double NOT NULL,
order_booking bigint NOT NULL,
responsible bigint NOT NULL,
constraint fk_orderBooking FOREIGN KEY (order_booking) references Hotel.Order_Booking(id),
constraint fk_responsible FOREIGN KEY (responsible) references Hotel.User(id)
)

