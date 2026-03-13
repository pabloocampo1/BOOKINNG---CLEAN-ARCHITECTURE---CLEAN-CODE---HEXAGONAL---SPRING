package com.booking_platform.application.port.in.userUseCase;

import com.booking_platform.application.dto.AuthResult;

public interface LoginUseCase {

    AuthResult execute(String username, String password);
}
