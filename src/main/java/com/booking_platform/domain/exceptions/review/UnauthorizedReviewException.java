package com.booking_platform.domain.exceptions.review;

import com.booking_platform.domain.exceptions.DomainException;

public class UnauthorizedReviewException extends DomainException {
	public UnauthorizedReviewException() {
		super("No tienes permisos para crear una reseña de esta propiedad. Solo puedes reseñar propiedades donde hayas completado una reserva.");
	}

	public UnauthorizedReviewException(String message) {
		super(message);
	}
}