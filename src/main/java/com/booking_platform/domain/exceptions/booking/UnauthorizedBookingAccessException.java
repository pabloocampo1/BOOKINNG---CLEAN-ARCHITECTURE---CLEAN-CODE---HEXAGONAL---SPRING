package com.booking_platform.domain.exceptions.booking;

import com.booking_platform.domain.exceptions.DomainException;

public class UnauthorizedBookingAccessException extends DomainException {
	public UnauthorizedBookingAccessException() {
		super("No tienes permisos para acceder a esta reserva. Solo el huésped puede realizar esta acción.");
	}

	public UnauthorizedBookingAccessException(String message) {
		super(message);
	}
}
