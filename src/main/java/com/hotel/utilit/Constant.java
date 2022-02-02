package com.hotel.utilit;

public class Constant {
    public static final Integer ID_DEFAULT_ROLE_CLIENT = 2;             //default value for new user: role = "CLIENT"
    public static final Integer ID_DEFAULT_USER_STATUS_ACTIVE = 1;      //default value for new user: status = "ACTIVE"
    public static final Integer ID_DEFAULT_ORDER_STATUS_WAIT = 1;       //default value for new orderBooking: status = "WAIT"
    public static final Integer ID_DEFAULT_ORDER_STATUS_PAID = 2;       //orderBooking status = "PAID"
    public static final Integer ID_DEFAULT_ORDER_STATUS_CANCEL = 3;     //orderBooking status = "CANCEL"
    public static final Integer MAX_DAYS_BOOKING_PERIOD = 30;           //maximum days of booking
    public static final Integer MAX_DAYS_DATE_ARRIVAL = 270;            //maximum days from today to dateArrival
    public static final Integer MAX_ITEMS_ON_PAGE = 10;                 //maximum items on the page for paginating
    public static final String EMAIL_BOOKING_SUBJECT = "Hotel booking"; //subject for email

}
