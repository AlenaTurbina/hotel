INSERT into 
Hotel2.Role (name) 
values 
("ROLE_ADMIN"),
("ROLE_CLIENT")


INSERT into 
Hotel2.User_Status (name) 
values 
("active"),
("blocked")

INSERT into 
Hotel2.User (email, password, first_name, last_name, user_status) 
values 
("kot@gmail.com", "$2a$12$vQNfFfq7FX13aMi7PyARHuqmXB52sS/ViUA4fQHPw6YS9G041a21i",  "Ivan", "Kot", 1),
("Los@mail.ru", "$2a$12$PLdxqa8JCi7VhAeq7kjbQ.DDi.O52NcI7RCU9Bilqa.MvvNnF69b.", "Anton", "Los", 1),
("Drozd@gmail.com", "$2a$12$0xMTUu7S/5nYhy2chIKR0OHG0B4H70AIHoxmxFr8aGsiXqUIGyBse", "Hanna", "Drozd", 1),
("Lis@gmail.com", "$2a$12$0xMTUu7S/5nYhy2chIKR0OHG0B4H70AIHoxmxFr8aGsiXqUIGyBse", "Lida", "lis", 1)


INSERT into 
Hotel2.User_With_Role (user_id, role_id) 
values 
(1, 1),
(2, 2),
(3, 2),
(4, 2)


INSERT into 
Hotel2.Room_Type (name, quantity_places) 
values 
("SGL", 1),
("DBL", 2),
("TWN", 2),
("TRPL", 3),
("TRPL+EXB", 4)

INSERT into 
Hotel2.Class_Apartment (name, place_price) 
values 
("classic", 20),
("de luxe", 30),
("grand", 50)

INSERT into 
Hotel2.Room_kind (room_type_id, class_apartment_id, room_price) 
values 
(1, 1, 70),
(1, 2, 80),
(1, 3, 100),
(2, 1, 80),
(2, 2, 90),
(2, 3, 150),
(3, 1, 80),
(3, 2, 90),
(3, 3, 150),
(4, 1, 100),
(4, 2, 120),
(4, 3, 170),
(5, 1, 100),
(5, 2, 120),
(5, 3, 170)

INSERT into 
Hotel2.Room (name, room_kind) 
values 
("11A", 1),
("11B", 1),
("11C", 1),
("11D", 1),
("11E", 1),
("11F", 1),
("12A", 2),
("12B", 2),
("12C", 2),
("12D", 2),
("13A", 3),
("13B", 3),
("21A", 4),
("21B", 4),
("22A", 5),
("22B", 5),
("23A", 6),
("23B", 6),
("31A", 7),
("31B", 7),
("32A", 8),
("32B", 8),
("33A", 9),
("33B", 9),
("41A", 10),
("41B", 10),
("42A", 11),
("42B", 11),
("43A", 12),
("43B", 12),
("51A", 13),
("51B", 13),
("52A", 14),
("52B", 14),
("53A", 15),
("53B", 15)


INSERT into 
Hotel2.Optional (name, optional_price) 
values 
("fridge", 5),
("iron", 3),
("washing", 3),
("dryCleaning", 5)

INSERT into 
Hotel2.Order_Status (name) 
values 
("wait"),
("paid"),
("cancel")

INSERT into 
Hotel2.Order_Booking (date, date_arrival, date_departure, quantity_persons, client, room, sum_total, order_status) 
values 
('2021-11-23', '2021-12-20', '2021-12-21', 2, 1, 1, 1000, 1 ),
('2021-11-23', '2021-12-20', '2021-12-21', 2, 2, 2, 1000, 1),
('2021-11-23', '2021-12-20', '2021-12-21', 2, 1, 3, 1000, 1 ),
('2021-11-23', '2021-12-20', '2021-12-21', 2, 1, 4, 1000, 1 )


INSERT into 
Hotel2.Order_Booking_With_Optional (order_booking_id, optional_id) 
values 
(1, 1),
(1, 3),
(2, 1),
(2, 3)
