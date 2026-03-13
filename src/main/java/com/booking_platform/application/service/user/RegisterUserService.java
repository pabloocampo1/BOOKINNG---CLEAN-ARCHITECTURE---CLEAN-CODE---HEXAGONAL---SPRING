package com.booking_platform.application.service.user;

import com.booking_platform.application.port.in.userUseCase.RegisterUserUseCase;
import com.booking_platform.application.port.out.EventPublisher;
import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.domain.Event.WelcomeUserEmailEvent;
import com.booking_platform.domain.exceptions.UserException.InvalidEmailException;
import com.booking_platform.domain.model.User;
import jakarta.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService implements RegisterUserUseCase {

   public final UserRepository userRepository;
   public final EventPublisher eventPublisher;
   private final PasswordEncoder passwordEncoder;


    public RegisterUserService(UserRepository userRepository, EventPublisher eventPublisher, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public User execute(User user) {

        this.validationEmailUnique(user.getEmail());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User userSaved = this.userRepository.save(user);

        WelcomeUserEmailEvent welcomeUserEmailEvent = new WelcomeUserEmailEvent(
                userSaved.getEmail(),
                userSaved.getName()
        );

        this.eventPublisher.publish(welcomeUserEmailEvent);

        return userSaved;
    }

    public void validationEmailUnique(String email) {
        if (this.userRepository.existByEmail(email)) throw new InvalidEmailException();
    }
}
