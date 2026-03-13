package com.booking_platform.infrastructure.rest.dto;

import com.booking_platform.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String name;
    private String lastName;
    private String email;
    private Long dni;

}
