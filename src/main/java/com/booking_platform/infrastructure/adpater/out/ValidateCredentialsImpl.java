package com.booking_platform.infrastructure.adpater.out;

import com.booking_platform.application.dto.AuthResult;
import com.booking_platform.application.port.out.auth.ValidateCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class ValidateCredentialsImpl implements ValidateCredentials {

    private final AuthenticationManager authenticationManager;

    public ValidateCredentialsImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResult execute(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

        Optional<? extends GrantedAuthority> role = authentication.getAuthorities().stream().findFirst();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (role.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return new AuthResult(userDetails.getUsername(), "", role.get().toString());
    }
}
