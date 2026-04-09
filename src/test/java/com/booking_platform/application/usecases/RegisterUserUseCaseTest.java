package com.booking_platform.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.booking_platform.application.port.out.EventPublisher;
import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.application.service.user.RegisterUserService;
import com.booking_platform.domain.Event.WelcomeUserEmailEvent;
import com.booking_platform.domain.exceptions.UserException.InvalidEmailException;
import com.booking_platform.domain.model.User;
import com.booking_platform.mother.UserMother;

@ExtendWith(MockitoExtension.class)
public class RegisterUserUseCaseTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private EventPublisher eventPublisher;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private RegisterUserService registerUserService;

  @Test
  void shouldCreateUserSuccessfully() {
    User user = UserMother.validUser();
    String passEncode = "password123Juan$#___Encoded";

    when(userRepository.existByEmail(user.getEmail())).thenReturn(false);
    when(userRepository.save(user)).thenReturn(user);
    when(passwordEncoder.encode(user.getPassword())).thenReturn(passEncode);

    User userSaved = this.registerUserService.execute(user);

    assertEquals(user.getName(), userSaved.getName());
    assertEquals(passEncode, userSaved.getPassword());

    verify(userRepository, times(1)).save(user);
    verify(userRepository, times(1)).existByEmail(user.getEmail());

    verify(eventPublisher, times(1)).publish(any(WelcomeUserEmailEvent.class));
  }

  @Test
  @DisplayName("Debería lanzar InvalidEmailException cuando el email ya existe")
  void shouldThrowEmailException() {
    User user = UserMother.validUser();
    user.setEmail("Juangamil.com");

    when(userRepository.existByEmail(user.getEmail())).thenReturn(true);

    assertThrows(InvalidEmailException.class, () -> this.registerUserService.execute(user));

    verify(userRepository, times(1)).existByEmail(user.getEmail());

    verify(userRepository, times(0)).save(any());
    verify(eventPublisher, times(0)).publish(any());

  }

}
