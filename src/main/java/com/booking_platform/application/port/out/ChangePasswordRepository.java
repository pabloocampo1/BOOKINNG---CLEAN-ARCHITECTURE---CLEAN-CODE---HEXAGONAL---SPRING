package com.booking_platform.application.port.out;

import com.booking_platform.domain.model.ChangeEmailCodeModel;
import com.booking_platform.infrastructure.persistence.entity.ChangeEmailCode;

import java.util.Optional;

public interface ChangePasswordRepository {
    Optional<ChangeEmailCodeModel> findByCode(Integer code);
    Optional<ChangeEmailCodeModel> findByEmail(String email);
    boolean existsByEmail(String email);
    ChangeEmailCodeModel save(ChangeEmailCodeModel changeEmailCodeModel);
    void delete(Long id);
    boolean existCode(Integer code);
    void deleteAllExpirated();

}
