package com.booking_platform.domain.exceptions.UserException;

public class NoPermissionManagePropertyException extends RuntimeException {
    public NoPermissionManagePropertyException(String message) {
        super(message);
    }
}
