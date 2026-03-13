package com.booking_platform.domain.exceptions.property;

import com.booking_platform.domain.exceptions.DomainException;

public class InvalidLocationException extends DomainException {
    public InvalidLocationException(String message) {
        super(message);
    }
}
