package com.booking_platform.application.dto;

public record AuthResult(
        String username,
        String token,
        String role
) {
}
