package com.booking_platform.application.service.user;

import com.booking_platform.application.dto.AuthResult;
import com.booking_platform.application.port.in.userUseCase.LoginUseCase;
import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.application.port.out.auth.GenerateJwt;
import com.booking_platform.application.port.out.auth.ValidateCredentials;
import com.booking_platform.domain.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class LoginService implements LoginUseCase {

    private final ValidateCredentials validateCredentials;
    private final GenerateJwt generateJwt;
    private final UserRepository userRepository;

    public LoginService(ValidateCredentials validateCredentials, GenerateJwt generateJwt, UserRepository userRepository) {
        this.validateCredentials = validateCredentials;
        this.generateJwt = generateJwt;
        this.userRepository = userRepository;
    }

    @Override
    public AuthResult execute(String username, String password) {

        AuthResult authResult =  this.validateCredentials.execute(username, password);



        String jwt = this.generateJwt.execute(authResult.username(), authResult.role());

        return new AuthResult(authResult.username(), jwt, authResult.role());
    }
}
