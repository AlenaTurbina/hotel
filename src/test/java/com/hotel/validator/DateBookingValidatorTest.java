package com.hotel.validator;

import com.hotel.configuration.TestConfigurationValidator;
import com.hotel.dto.OrderBookingDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.validation.Errors;

import java.time.LocalDate;

import static com.hotel.utilit.Constant.MAX_DAYS_BOOKING_PERIOD;
import static com.hotel.utilit.Constant.MAX_DAYS_DATE_ARRIVAL;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfigurationValidator.class,
        loader = AnnotationConfigContextLoader.class)
class DateBookingValidatorTest {

    @Autowired
    private DateBookingValidator dateBookingValidator;

    private static final OrderBookingDTO orderBookingDTO = mock(OrderBookingDTO.class);
    private static final LocalDate dateArrival = LocalDate.of(2022, 01, 10);
    private static final LocalDate dateDepartureValid = LocalDate.of(2022, 01, 12);
    private static final LocalDate dateDepartureInvalid = LocalDate.of(2022, 01, 8);

    @Test
    void testValidateShouldAcceptDatesWhenDateArrivalBeforeDateDeparture() {
        when(orderBookingDTO.getDateArrival()).thenReturn(dateArrival);
        when(orderBookingDTO.getDateDeparture()).thenReturn(dateDepartureValid);
        Errors errors = mock(Errors.class);
        dateBookingValidator.validate(orderBookingDTO, errors);

        verify(errors, never())
                .rejectValue("dateDeparture", "validation.booking.dateDeparture.min");
    }

    @Test
    void testValidateShouldRejectDatesWhenDateArrivalEqualDateDeparture() {
        when(orderBookingDTO.getDateArrival()).thenReturn(dateArrival);
        when(orderBookingDTO.getDateDeparture()).thenReturn(dateArrival);
        Errors errors = mock(Errors.class);
        dateBookingValidator.validate(orderBookingDTO, errors);

        verify(errors, times(1))
                .rejectValue("dateDeparture", "validation.booking.dateDeparture.min");
    }

    @Test
    void testValidateShouldRejectDatesWhenDateArrivalAfterDateDeparture() {
        when(orderBookingDTO.getDateArrival()).thenReturn(dateArrival);
        when(orderBookingDTO.getDateDeparture()).thenReturn(dateDepartureInvalid);
        Errors errors = mock(Errors.class);
        dateBookingValidator.validate(orderBookingDTO, errors);

        verify(errors, times(1))
                .rejectValue("dateDeparture", "validation.booking.dateDeparture.min");
    }

    @Test
    void testValidateShouldAcceptDatesWhenDateArrivalEqualToday() {
        when(orderBookingDTO.getDateArrival()).thenReturn(LocalDate.now());
        when(orderBookingDTO.getDateDeparture()).thenReturn(LocalDate.now().plusDays(1));
        Errors errors = mock(Errors.class);
        dateBookingValidator.validate(orderBookingDTO, errors);

        verify(errors, never())
                .rejectValue("dateArrival", "validation.booking.dateArrival.min");
    }

    @Test
    void testValidateShouldRejectDatesWhenDateBookingPeriodExceedMax() {
        when(orderBookingDTO.getDateArrival()).thenReturn(dateArrival);
        when(orderBookingDTO.getDateDeparture()).thenReturn(dateArrival.plusDays(MAX_DAYS_BOOKING_PERIOD + 1));
        Errors errors = mock(Errors.class);
        dateBookingValidator.validate(orderBookingDTO, errors);

        verify(errors, times(1))
                .rejectValue("dateDeparture", "validation.booking.dateDeparture.max");
    }

    @Test
    void testValidateShouldRejectDatesWhenDateArrivalExceedMax() {
        when(orderBookingDTO.getDateArrival()).thenReturn(LocalDate.now().plusDays(MAX_DAYS_DATE_ARRIVAL + 1));
        when(orderBookingDTO.getDateDeparture()).thenReturn(LocalDate.now().plusDays(MAX_DAYS_DATE_ARRIVAL + 2));
        Errors errors = mock(Errors.class);
        dateBookingValidator.validate(orderBookingDTO, errors);

        verify(errors, times(1))
                .rejectValue("dateArrival", "validation.booking.dateArrival.max");
    }

}