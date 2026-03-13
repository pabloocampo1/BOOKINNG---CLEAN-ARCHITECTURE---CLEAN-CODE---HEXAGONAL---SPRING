package com.booking_platform.domain.exceptions.property;

public class PropertyStateException extends RuntimeException {
    public PropertyStateException(String message) {
        super(message);
    }
}
