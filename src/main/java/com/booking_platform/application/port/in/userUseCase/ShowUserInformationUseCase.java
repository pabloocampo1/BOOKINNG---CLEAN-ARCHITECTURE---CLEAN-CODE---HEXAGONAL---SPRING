package com.booking_platform.application.port.in.userUseCase;

import com.booking_platform.domain.model.User;

public interface ShowUserInformationUseCase {

    User execute(Long userId);
}
