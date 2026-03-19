package com.booking_platform.application.service.user;

import com.booking_platform.application.port.in.userUseCase.ShowUserInformationUseCase;
import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.domain.model.User;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class ShowUserInformationService implements ShowUserInformationUseCase {

    private final UserRepository userRepository;

    public ShowUserInformationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute(Long userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento."));
    }
}
