INSERT into 
Hotel.User_Role (name) 
values 
("client"),
("administrator")

INSERT into 
Hotel.User_Status (name) 
values 
("active"),
("delete")

INSERT into 
Hotel.User (first_name, last_name, login, password, email, phone_number, document, user_role, user_status) 
values 
("Ivan", "Kot", "KotI", "123456FFF", "kot@gmail.com", "80295555555", "AB123563", 1, 1),
("Anton", "Los", "LosA", "123AABFGGGF", "Los@mail.ru", "80297777777", "AB145265", 1, 1),
("Hanna", "Lis", "LisH", "1GGDDD15268", "lis@gmail.com", "80332222222", "MP456728", 1, 2)

INSERT into 
Hotel.Room_Type (name, quantity_places) 
values 
("SGL", 1),
("DBL", 2),
("DBL TWN", 2),
("TRPL", 3),
("DBL + EXB", 3),
("TRPL + EXB", 4)

INSERT into 
Hotel.Class_Apartment (name, place_price) 
values 
("econom", 10),
("standart", 20),
("de luxe", 30),
("president", 100)

INSERT into 
Hotel.Room (name, room_price, room_type, class_apartment) 
values 
("11A", 50, 1, 1),
("11B", 55, 1, 1),
("12A", 55, 1, 2),
("12B", 60, 1, 2),
("14A", 70, 1, 3),
("14B", 80, 1, 3),
("21A", 60, 2, 1),
("21B", 62, 2, 1),
("22A", 75, 2, 2),
("22B", 80, 2, 2),
("23A", 80, 2, 3),
("23B", 85, 2, 3),
("31A", 90, 3, 1),
("31B", 95, 3, 1),
("32A", 70, 3, 2),
("32B", 70, 3, 2),
("33A", 80, 3, 3),
("33B", 85, 3, 3),
("41A", 80, 4, 1),
("41B", 85, 4, 1),
("42A", 90, 4, 2),
("42B", 95, 4, 2),
("43A", 80, 4, 3),
("43B", 85, 4, 3)

INSERT into 
Hotel.Optional (name, optional_price) 
values 
("fridge", 5),
("iron", 3),
("washing", 3),
("dryCleaning", 5)

INSERT into 
Hotel.Order_Status (name) 
values 
("waiting"),
("paid"),
("refused")

INSERT into 
Hotel.Order_Booking (date, date_arrival, date_departure, quantity_persons, client, room, order_status) 
values 
('2021-11-22', '2021-11-29', '2021-12-01', 2, 1, 6, 1 ),
('2021-11-22', '2021-11-22', '2021-11-23', 2, 2, 5, 1)

INSERT into 
Hotel.Order_Booking_With_Optional (order_booking_id, optional_id) 
values 
(1, 1),
(1, 3),
(2, 1),
(2, 3)

INSERT into 
Hotel.Invoice (sum_total, order_booking, responsible) 
values 
(1000, 1, 3),
(2000, 2, 3)
