package com.booking_platform.application.port.in.userUseCase;

import com.booking_platform.infrastructure.rest.dto.ChangePasswordDto;

public interface ChangePasswordUseCase {
    void execute(ChangePasswordDto dto);
}
