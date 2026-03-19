package com.booking_platform.application.service.user;

import com.booking_platform.application.dto.AuthResult;
import com.booking_platform.application.port.in.userUseCase.LoginUseCase;

import com.booking_platform.application.port.out.auth.GenerateJwt;
import com.booking_platform.application.port.out.auth.ValidateCredentials;

import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginUseCase {

    private final ValidateCredentials validateCredentials;
    private final GenerateJwt generateJwt;

    public LoginService(ValidateCredentials validateCredentials, GenerateJwt generateJwt) {
        this.validateCredentials = validateCredentials;
        this.generateJwt = generateJwt;

    }

    @Override
    public AuthResult execute(String username, String password) {

        AuthResult authResult = this.validateCredentials.execute(username, password);

        String jwt = this.generateJwt.execute(authResult.username(), authResult.role());

        return new AuthResult(authResult.username(), jwt, authResult.role());
    }
}
