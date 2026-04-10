package com.booking_platform.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.booking_platform.application.port.out.PropertyRepository;
import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.application.service.propertyServices.CreatePropertyService;
import com.booking_platform.domain.model.Property;
import com.booking_platform.domain.model.User;
import com.booking_platform.mother.PropertyMother;
import com.booking_platform.mother.UserMother;

@ExtendWith(MockitoExtension.class)
public class CreatePropertyUseCaseTest {

  @Mock
  private PropertyRepository propertyRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private CreatePropertyService createPropertyService;

  @Test
  void shouldCreatePropertySuccessfully() {

    String owner = "Juan Castro";

    Property property = PropertyMother.validPropertyWithOutId();
    Property propertySaved = PropertyMother.validProperty();

    User user = UserMother.validUserWithUsername(owner);
    user.becomeHost();

    when(userRepository.findByUsername(owner))
        .thenReturn(Optional.of(user));

    when(propertyRepository.save(any(Property.class)))
        .thenReturn(propertySaved);

    Property result = this.createPropertyService.execute(owner, property);

    assertEquals(result.getTitle(), propertySaved.getTitle());
    assertEquals(1L, result.getPropertyId());
    assertNotNull(result.getPropertyId());

    verify(userRepository, times(1)).findByUsername(owner);
    verify(propertyRepository, times(1)).save(any(Property.class));

  }

}
