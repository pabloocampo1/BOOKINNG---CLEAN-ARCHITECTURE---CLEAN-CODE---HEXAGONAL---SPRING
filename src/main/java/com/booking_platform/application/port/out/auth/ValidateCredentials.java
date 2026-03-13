package com.booking_platform.application.port.out.auth;

import com.booking_platform.application.dto.AuthResult;

public interface ValidateCredentials {

    AuthResult execute(String username, String password);

}
