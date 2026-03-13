package com.booking_platform.domain.exceptions.UserException;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super("Email no valido.");
    }
}
