package com.booking_platform.domain.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.booking_platform.domain.exceptions.UserException.InvalidEmailException;
import com.booking_platform.domain.exceptions.UserException.PasswordUserException;
import com.booking_platform.domain.model.Role;
import com.booking_platform.domain.model.User;
import com.booking_platform.mother.UserMother;

public class UserModelTest {

  @Test
  void validEmailTest() {

    assertThrows(InvalidEmailException.class, () -> new User("juan", "ocampo", 12322323L, "juangmail.com", LocalDateTime.now(), "password123", "admin" ));
    
  }

  @Test
@DisplayName("Debería lanzar IllegalArgumentException cuando la contraseña es vacía")
void shouldThrowException_WhenPasswordIsEmpty() {
    assertThrows(IllegalArgumentException.class, () -> 
        new User("juan", "ocampo", 123L, "test@mail.com", LocalDateTime.now(), "", "admin")
    );
}

@Test
@DisplayName("Debería lanzar PasswordUserException cuando la contraseña es muy corta")
void shouldThrowException_WhenPasswordIsTooShort() {
    assertThrows(PasswordUserException.class, () -> 
        new User("juan", "ocampo", 123L, "test@mail.com", LocalDateTime.now(), "Short1!", "admin")
    );
}

@Test
@DisplayName("Debería lanzar PasswordUserException cuando falta una mayúscula")
void shouldThrowException_WhenPasswordLacksUppercase() {
    assertThrows(PasswordUserException.class, () -> 
        new User("juan", "ocampo", 123L, "test@mail.com", LocalDateTime.now(), "lowercase1!", "admin")
    );
}

@Test
@DisplayName("Debería convertirse en host")
void shouldUserBecomeHost() {

  User user = UserMother.validUser();

   user.becomeHost();

   assertEquals(Role.HOST, user.getRole());

}


@Test
void shouldUserManagePropertiesWithRoleGuest() {
  User user = UserMother.validUser();

  assertFalse(user.canManageProperties());
}

@Test
void shouldUserManagePropertiesWithRoleHost() {
  User user = UserMother.validUser();
  user.becomeHost();

  assertTrue( user.canManageProperties());
}

@Test
void shouldUserActive() {
  User user = UserMother.validUser();
  user.active();
  assertTrue(user.isState());
}

@Test
void shouldUserInactive() {
  User user = UserMother.validUser();
  user.inactive();
  assertFalse(user.isState()); 
}

@Test
void shouldReturnFullName(){
  User user = UserMother.validUser();
  String fullNameExpect = user.getName() + " "+ user.getLastName();
  assertEquals(fullNameExpect, user.getFullName());
}









  
}
