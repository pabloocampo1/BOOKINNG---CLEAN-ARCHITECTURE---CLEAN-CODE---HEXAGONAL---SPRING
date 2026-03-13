package com.booking_platform.infrastructure.rest.dto;

import com.booking_platform.domain.model.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRegisterDto {

    private String name;
    private String lastName;
    private String email;
    private Long dni;
    private String password;
    private String username;

}
