package com.booking_platform.application.port.out.auth;

import com.booking_platform.domain.model.User;

public interface GenerateJwt {

    String execute(String username, String role);
}
