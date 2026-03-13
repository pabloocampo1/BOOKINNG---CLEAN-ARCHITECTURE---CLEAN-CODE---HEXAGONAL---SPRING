package com.booking_platform.domain.exceptions;

public class TokenChangePasswordExpired extends RuntimeException {
    public TokenChangePasswordExpired() {
        super("Su codigo esta vencido, por favor solicita uno nuevo.");
    }
}
