INSERT into 
Hotel.Role (name) 
values 
("ROLE_ADMIN"),
("ROLE_CLIENT")


INSERT into 
Hotel.User_Status (name) 
values 
("active"),
("blocked")

INSERT into 
Hotel.User (email, password, first_name, last_name, phone_number, document, user_status) 
values 
("kot@gmail.com", "$2a$12$vQNfFfq7FX13aMi7PyARHuqmXB52sS/ViUA4fQHPw6YS9G041a21i",  "Ivan", "Kot", "+375771111111", "AB1111111", 1),
("Los@gmail.ru", "$2a$12$iVXAHUEw2sAYEdPSxyuuGelRlBgo7QX0ehqLx57Q19P1.xb.UguZS", "Anton", "Los", "+375772222222", "AB222222", 1),
("Drozd@gmail.com", "$2a$12$iVXAHUEw2sAYEdPSxyuuGelRlBgo7QX0ehqLx57Q19P1.xb.UguZS", "Hanna", "Drozd", "+375773333333", "MC3333333", 1),
("Lis@gmail.com", "$2a$12$iVXAHUEw2sAYEdPSxyuuGelRlBgo7QX0ehqLx57Q19P1.xb.UguZS", "Zoya", "Lis", "+375774444444", "MC4444444", 1),
("Volk@gmail.com", "$2a$12$iVXAHUEw2sAYEdPSxyuuGelRlBgo7QX0ehqLx57Q19P1.xb.UguZS",  "Liza", "Volk", "+375775555555", "MC5555555", 2)



INSERT into 
Hotel.User_With_Role (user_id, role_id) 
values 
(1, 1),
(2, 2),
(3, 2),
(4, 2),
(5, 1)


INSERT into 
Hotel.Room_Type (name, quantity_places) 
values 
("SGL", 1),
("DBL", 2),
("TWN", 2),
("TRPL", 3),
("TRPL+EXB", 4)

INSERT into 
Hotel.Class_Apartment (name, place_price) 
values 
("classic", 10),
("de luxe", 20),
("grand", 50)

INSERT into 
Hotel.Room_kind (room_type_id, class_apartment_id, room_price) 
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
Hotel.Room (name, room_kind) 
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
Hotel.Optional (name, optional_price) 
values 
("fridge", 5),
("iron", 3),
("washing", 3),
("dryCleaning", 5)

INSERT into 
Hotel.Order_Status (name) 
values 
("wait"),
("paid"),
("cancel")

INSERT into 
Hotel.Order_Booking (date, date_arrival, date_departure, quantity_persons, client, room, sum_total, order_status) 
values 
('2021-12-23', '2022-01-05', '2022-01-07', 2, 1, 1, 1000, 1 ),
('2021-12-23', '2022-01-05', '2022-01-07', 2, 2, 2, 1000, 1 ),
('2021-12-23', '2022-01-05', '2022-01-07', 2, 3, 3, 1000, 1 ),
('2021-12-23', '2022-01-05', '2022-01-07', 2, 1, 4, 1000, 1 )


INSERT into 
Hotel.Order_Booking_With_Optional (order_booking_id, optional_id) 
values 
(1, 1),
(1, 3),
(2, 1),
(2, 3)




