package com.booking_platform.domain.exceptions.booking;

import com.booking_platform.domain.exceptions.DomainException;

public class InvalidBookingDatesException extends DomainException {
    public InvalidBookingDatesException() {
        super("Las fechas de check-in y check-out no son válidas.");
    }

    public InvalidBookingDatesException(String message) {
        super(message);
    }
}
