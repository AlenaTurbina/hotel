CREATE Database Hotel2

CREATE TABLE Hotel2.Role (
id bigint PRIMARY KEY Auto_increment,
name varchar (256) NOT NULL UNIQUE
)

CREATE TABLE Hotel2.User_Status (
id bigint PRIMARY KEY Auto_increment,
name varchar (256) NOT NULL UNIQUE
)

CREATE TABLE Hotel2.User (
id bigint PRIMARY KEY Auto_increment,
email varchar (256) NOT NUll UNIQUE,
password varchar (256) NOT NULL,
first_name varchar (256) NOT NULL,
last_name varchar (256) NOT NULL,
user_status bigint NOT NULL,
constraint fk_userStatus FOREIGN KEY (user_status) references Hotel2.User_Status(id)
)

CREATE TABLE Hotel2.User_With_Role (
id bigint PRIMARY KEY Auto_increment,
user_id bigint NOT NULL,
role_id bigint NOT NULL,
constraint fk_userID FOREIGN KEY (user_id) references Hotel2.User(id),
constraint fk_roleID FOREIGN KEY (role_id) references Hotel2.Role(id),
UNIQUE (user_id, role_id)
)

CREATE TABLE Hotel2.Room_Type (
id bigint PRIMARY KEY Auto_increment,
name varchar (256) NOT NULL UNIQUE,
quantity_places integer NOT NULL,
check (quantity_places>0)
)

CREATE TABLE Hotel2.Class_Apartment (
id bigint PRIMARY KEY Auto_increment,
name varchar (256) NOT NULL UNIQUE,
place_price double NOT NULL,
check (place_price>0)
)

CREATE TABLE Hotel2.Room_kind (
id bigint PRIMARY KEY Auto_increment,
room_type_id bigint NOT NULL,
class_apartment_id bigint NOT NULL,
room_price double NOT NULL,
constraint fk_roomType FOREIGN KEY (room_type_id) references Hotel2.Room_Type(id),
constraint fk_classApartment FOREIGN KEY (class_apartment_id) references Hotel2.Class_Apartment(id),
UNIQUE (room_type_id, class_apartment_id),
check (room_price>0)
)

CREATE TABLE Hotel2.Room (
id bigint PRIMARY KEY Auto_increment,
name varchar (10) NOT NULL UNIQUE,
room_kind bigint NOT NULL,
constraint fk_roomKind FOREIGN KEY (room_kind) references Hotel2.Room_kind(id)
)

CREATE TABLE Hotel2.Optional (
id bigint PRIMARY KEY Auto_increment,
name varchar (256) NOT NULL UNIQUE,
optional_price double NOT NULL,
check (optional_price>0)
)

CREATE TABLE Hotel2.Order_Status (
id bigint PRIMARY KEY Auto_increment,
name varchar (256) NOT NULL UNIQUE
)

CREATE TABLE Hotel2.Order_Booking (
id bigint PRIMARY KEY Auto_increment,
date date NOT NULL,
date_arrival date NOT NULL,
date_departure date NOT NULL,
quantity_persons integer NOT NULL,
client bigint NOT NULL,
room bigint NOT NULL,
sum_total double NOT NULL,
order_status bigint NOT NULL,
constraint fk_client FOREIGN KEY (client) references Hotel2.User(id),
constraint fk_room FOREIGN KEY (room) references Hotel2.Room(id),
constraint fk_orderStatus FOREIGN KEY (order_status) references Hotel2.Order_Status(id),
check (date_departure>date_arrival)
)

CREATE TABLE Hotel2.Order_Booking_With_Optional (
id bigint PRIMARY KEY auto_increment,
order_booking_id bigint NOT NULL,
optional_id bigint NOT NULL,
constraint fk_orderBookingID FOREIGN KEY (order_booking_id) references Hotel2.Order_Booking(id),
constraint fk_optionalID FOREIGN KEY (optional_id) references Hotel2.Optional(id),
UNIQUE (order_booking_id, optional_id)
)


