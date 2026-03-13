package com.booking_platform.infrastructure.adpater.out;

import com.booking_platform.application.port.out.auth.GenerateJwt;
import com.booking_platform.domain.model.User;
import com.booking_platform.infrastructure.security.jwt.JwtUtils;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

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
