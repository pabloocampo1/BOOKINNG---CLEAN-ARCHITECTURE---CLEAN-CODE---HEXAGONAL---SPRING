package com.booking_platform.mother;

import java.time.LocalDateTime;

import com.booking_platform.domain.model.User;

public class UserMother {
  public static User validUser() {
    return new User("juan", "ocampo", 12322323L, "juang@mail.com", LocalDateTime.now(), "password123Juan$#", "admin");
  }

  public static User validUserWithUsername(String username) {
    return new User("juan", "ocampo", 12322323L, "juang@mail.com", LocalDateTime.now(), "password123Juan$#", username);
  }
}
