package com.hotel.configuration;

import com.hotel.service.*;
import com.hotel.validator.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestConfigurationValidator {

    //ClassApartment
    @Bean
    public ClassApartmentService classApartmentService() {
        return mock(ClassApartmentService.class);
    }

    @Bean
    public ClassApartmentValidator classApartmentValidator() {
        return new ClassApartmentValidator(classApartmentService());
    }

    @Bean
    public ClassApartmentUpdateValidator classApartmentUpdateValidator() {
        return new ClassApartmentUpdateValidator(classApartmentService());
    }

    //RoomType
    @Bean
    public RoomTypeService roomTypeService() {
        return mock(RoomTypeService.class);
    }

    @Bean
    public RoomTypeValidator roomTypeValidator() {
        return new RoomTypeValidator(roomTypeService());
    }

    @Bean
    public RoomTypeUpdateValidator roomTypeUpdateValidator() {
        return new RoomTypeUpdateValidator(roomTypeService());
    }

    //Optional
    @Bean
    public OptionalService optionalService() {
        return mock(OptionalService.class);
    }

    @Bean
    public OptionalValidator optionalValidator() {
        return new OptionalValidator(optionalService());
    }

    @Bean
    public OptionalUpdateValidator optionalUpdateValidator() {
        return new OptionalUpdateValidator(optionalService());
    }

    //RoomKind
    @Bean
    public RoomKindService roomKindService() {
        return mock(RoomKindService.class);
    }

    @Bean
    public RoomKindValidator roomKindValidator() {
        return new RoomKindValidator(roomKindService());
    }

    @Bean
    public RoomKindUpdateValidator roomKindUpdateValidator() {
        return new RoomKindUpdateValidator(roomKindService());
    }

    //Room
    @Bean
    public RoomService roomService() {
        return mock(RoomService.class);
    }

    @Bean
    public RoomValidator roomValidator() {
        return new RoomValidator(roomService());
    }

    @Bean
    public RoomUpdateValidator roomUpdateValidator() {
        return new RoomUpdateValidator(roomService());
    }

    //OrderBooking / DateBooking
    @Bean
    public OrderBookingService orderBookingService() {
        return mock(OrderBookingService.class);
    }

    @Bean
    public DateBookingValidator dateBookingValidator() {
        return new DateBookingValidator();
    }

    @Bean
    public OrderBookingValidator orderBookingValidator() {
        return new OrderBookingValidator(roomTypeService());
    }

    //User
    @Bean
    public UserService userService() {
        return mock(UserService.class);
    }

    @Bean
    public UserValidator userValidator() {
        return new UserValidator(userService());
    }

    @Bean
    public UserClientUpdateValidator userClientUpdateValidator() {
        return new UserClientUpdateValidator(userService());
    }

}
