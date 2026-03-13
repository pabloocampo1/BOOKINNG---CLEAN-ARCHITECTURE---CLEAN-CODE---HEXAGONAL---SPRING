package com.booking_platform.infrastructure.adpater.in.controllers;

import com.booking_platform.application.dto.AuthResult;
import com.booking_platform.application.port.in.userUseCase.ChangePasswordUseCase;
import com.booking_platform.application.port.in.userUseCase.LoginUseCase;
import com.booking_platform.application.port.in.userUseCase.RequestPasswordResetUseCase;
import com.booking_platform.infrastructure.rest.dto.ChangePasswordDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RequestPasswordResetUseCase requestPasswordResetUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;

    public AuthController(LoginUseCase loginUseCase, RequestPasswordResetUseCase requestPasswordResetUseCase,
            ChangePasswordUseCase changePasswordUseCase) {
        this.loginUseCase = loginUseCase;
        this.requestPasswordResetUseCase = requestPasswordResetUseCase;
        this.changePasswordUseCase = changePasswordUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResult> login(@RequestParam(name = "username", required = true) String username,
            @RequestParam(value = "password", required = true) String password

    ) {

         AuthResult token = loginUseCase.execute(username, password);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/forgot-password/{email}")
    public ResponseEntity<Integer> forgotPassword(@PathVariable("email") String email) {
        return new ResponseEntity<>(this.requestPasswordResetUseCase.execute(email), HttpStatus.OK);
    }

   @PostMapping("/change-password")
   public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordDto dto) {
       this.changePasswordUseCase.execute(dto);
       return ResponseEntity.ok().build();
   }

}
