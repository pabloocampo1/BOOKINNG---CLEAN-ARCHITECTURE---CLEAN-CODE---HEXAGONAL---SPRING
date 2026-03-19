package com.booking_platform.infrastructure.rest.dto;

import lombok.Data;

@Data
public class UserRegisterDto {

    private String name;
    private String lastName;
    private String email;
    private Long dni;
    private String password;
    private String username;

}
