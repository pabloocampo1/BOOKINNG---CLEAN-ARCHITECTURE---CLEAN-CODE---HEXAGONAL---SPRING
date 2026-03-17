package com.booking_platform.domain.exceptions.property;

import com.booking_platform.domain.exceptions.DomainException;

public class AvailabilityRangeNotAvailableException extends DomainException {
    public AvailabilityRangeNotAvailableException() {
        super("No puedes bloquear en ese rango de fechas porque ya existe una reserva o un bloqueo dentro de ese rango.");
    }

    public AvailabilityRangeNotAvailableException(String message) {
        super(message);
    }
}

