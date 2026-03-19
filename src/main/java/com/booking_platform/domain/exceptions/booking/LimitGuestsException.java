package com.booking_platform.domain.exceptions.booking;

import com.booking_platform.domain.exceptions.DomainException;

public class LimitGuestsException extends DomainException {
	public LimitGuestsException(String message) {
		super(message);
	}

}
