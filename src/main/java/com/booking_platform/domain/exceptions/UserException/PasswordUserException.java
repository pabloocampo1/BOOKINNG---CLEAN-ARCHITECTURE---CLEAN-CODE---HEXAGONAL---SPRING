package com.booking_platform.domain.exceptions.UserException;

public class PasswordUserException extends RuntimeException {
    public PasswordUserException(String message) {
        super(message);
    }
}
