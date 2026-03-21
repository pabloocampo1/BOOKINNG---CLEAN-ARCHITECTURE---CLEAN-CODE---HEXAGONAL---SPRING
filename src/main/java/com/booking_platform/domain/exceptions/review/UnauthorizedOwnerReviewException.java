package com.booking_platform.domain.exceptions.review;

import com.booking_platform.domain.exceptions.DomainException;

public class UnauthorizedOwnerReviewException extends DomainException {
	public UnauthorizedOwnerReviewException() {
		super("No puedes crear una reseña de tu propia propiedad. Solo los huéspedes pueden hacer reseñas.");
	}

	public UnauthorizedOwnerReviewException(String message) {
		super(message);
	}
}