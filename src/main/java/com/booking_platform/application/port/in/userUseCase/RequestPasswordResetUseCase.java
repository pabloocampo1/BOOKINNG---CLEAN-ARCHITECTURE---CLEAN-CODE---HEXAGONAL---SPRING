package com.booking_platform.application.port.in.userUseCase;

public interface RequestPasswordResetUseCase {
    Integer execute(String email);
}
