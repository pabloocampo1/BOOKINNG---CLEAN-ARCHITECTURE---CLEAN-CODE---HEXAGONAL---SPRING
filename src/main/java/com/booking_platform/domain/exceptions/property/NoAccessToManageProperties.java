package com.booking_platform.domain.exceptions.property;

public class NoAccessToManageProperties extends RuntimeException {
    public NoAccessToManageProperties() {
        super("No tienes el rol para administrar propiedades");
    }
}
