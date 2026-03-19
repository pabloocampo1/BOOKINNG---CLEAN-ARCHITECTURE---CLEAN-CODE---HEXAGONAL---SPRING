package com.booking_platform.application.port.out.auth;

public interface GenerateJwt {

    String execute(String username, String role);
}
