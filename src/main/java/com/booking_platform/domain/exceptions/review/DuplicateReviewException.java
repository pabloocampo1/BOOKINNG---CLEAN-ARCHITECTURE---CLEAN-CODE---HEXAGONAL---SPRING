package com.booking_platform.domain.exceptions.review;

import com.booking_platform.domain.exceptions.DomainException;

public class DuplicateReviewException extends DomainException {
	public DuplicateReviewException() {
		super("Ya has creado una reseña para esta propiedad.");
	}

	public DuplicateReviewException(String message) {
		super(message);
	}
}