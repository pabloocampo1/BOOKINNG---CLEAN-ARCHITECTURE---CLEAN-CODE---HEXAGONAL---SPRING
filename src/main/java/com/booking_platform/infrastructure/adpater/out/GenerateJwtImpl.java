package com.booking_platform.infrastructure.adpater.out;

import org.springframework.stereotype.Component;

import com.booking_platform.application.port.out.auth.GenerateJwt;
import com.booking_platform.infrastructure.security.jwt.JwtUtils;

@Component
public class GenerateJwtImpl implements GenerateJwt {

    private final JwtUtils jwtUtils;

    public GenerateJwtImpl(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public String execute(String username, String role) {
        return this.jwtUtils.createJwt(username, role);
    }
}
