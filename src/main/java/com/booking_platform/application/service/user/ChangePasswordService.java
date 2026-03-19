package com.booking_platform.application.service.user;

import com.booking_platform.application.port.in.userUseCase.ChangePasswordUseCase;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.booking_platform.application.port.out.ChangePasswordRepository;
import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.domain.model.ChangeEmailCodeModel;
import com.booking_platform.domain.model.User;
import com.booking_platform.infrastructure.rest.dto.ChangePasswordDto;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ChangePasswordService implements ChangePasswordUseCase {
    private final ChangePasswordRepository changePasswordRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ChangePasswordService(ChangePasswordRepository changePasswordRepository, UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.changePasswordRepository = changePasswordRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void execute(ChangePasswordDto dto) {

        ChangeEmailCodeModel changeEmailCodeModel = this.changePasswordRepository.findByCode(dto.code())
                .orElseThrow(() -> new EntityNotFoundException("Error de validacion de codigo"));
        changeEmailCodeModel.validateTokenExpiration();

        User user = this.userRepository.findUserByEmail(changeEmailCodeModel.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Error de validacion de codigo"));

        user.validatePassword(dto.newPassword());
        user.setPassword(passwordEncoder.encode(dto.newPassword()));
        this.userRepository.save(user);

        this.changePasswordRepository.delete(changeEmailCodeModel.getId());

    }
}
