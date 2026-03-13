package com.booking_platform.domain.exceptions;

public abstract class DomainException extends RuntimeException {
    private final String code;

    public DomainException(String message) {
        super(message);
        this.code = this.getClass().getSimpleName();
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
        this.code = this.getClass().getSimpleName();
    }

    public String getCode() {
        return code;
    }
}
