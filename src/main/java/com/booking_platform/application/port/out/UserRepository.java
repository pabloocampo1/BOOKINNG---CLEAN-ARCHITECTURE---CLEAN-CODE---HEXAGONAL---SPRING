package com.booking_platform.application.port.out;

import com.booking_platform.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    boolean existByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findUserByEmail(String email);

}
