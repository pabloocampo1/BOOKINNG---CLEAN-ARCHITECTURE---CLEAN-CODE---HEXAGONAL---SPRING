package com.booking_platform.infrastructure.security.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Component
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String KEY_JWT;

    public String createJwt(String username, String role) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(KEY_JWT);

            String token = JWT.create()
                    .withSubject(username)
                    .withClaim("role", role)
                    .withIssuer("app-booking")
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validateJwt(String jwt) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(KEY_JWT);
            JWT.require(algorithm)
                    .build()
                    .verify(jwt);

            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String getUsername(String jwt) {
        Algorithm algorithm = Algorithm.HMAC256(KEY_JWT);

        return JWT.require(algorithm).build().verify(jwt).getSubject();
    }
}
