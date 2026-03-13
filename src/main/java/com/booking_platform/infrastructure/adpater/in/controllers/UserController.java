package com.booking_platform.infrastructure.adpater.in.controllers;

import com.booking_platform.application.port.in.userUseCase.BecomeToHostUseCase;
import com.booking_platform.application.port.in.userUseCase.RegisterUserUseCase;
import com.booking_platform.application.port.in.userUseCase.ShowUserInformationUseCase;
import com.booking_platform.domain.model.User;
import com.booking_platform.infrastructure.rest.dto.UserRegisterDto;
import com.booking_platform.infrastructure.rest.dto.UserResponse;
import com.booking_platform.infrastructure.rest.mapperRest.UserMapperRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {


    private final UserMapperRest userMapperRest;
    private final RegisterUserUseCase registerUserUseCase;
    private final BecomeToHostUseCase becomeToHostUseCase;
    private final ShowUserInformationUseCase showUserInformation;

    public UserController(UserMapperRest userMapperRest, RegisterUserUseCase registerUserUseCase, BecomeToHostUseCase becomeToHostUseCase, ShowUserInformationUseCase showUserInformation) {
        this.userMapperRest = userMapperRest;
        this.registerUserUseCase = registerUserUseCase;
        this.becomeToHostUseCase = becomeToHostUseCase;
        this.showUserInformation = showUserInformation;
    }

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody UserRegisterDto userRegisterDto){
        User userModel = this.userMapperRest.userRegisterDtoToModel(userRegisterDto);
        return new ResponseEntity<>(this.registerUserUseCase.execute(userModel), HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse>  getUserInformation(@RequestParam(value = "userId", required = true) Long userId) {
        return new ResponseEntity<>(this.userMapperRest.toResponse(this.showUserInformation.execute(userId)), HttpStatus.OK);
    }

    @PostMapping("/become-host")
    public ResponseEntity<String> becomeHost(@RequestParam Long userId){
        return new ResponseEntity<>(this.becomeToHostUseCase.execute(userId), HttpStatus.OK);
    }


}
