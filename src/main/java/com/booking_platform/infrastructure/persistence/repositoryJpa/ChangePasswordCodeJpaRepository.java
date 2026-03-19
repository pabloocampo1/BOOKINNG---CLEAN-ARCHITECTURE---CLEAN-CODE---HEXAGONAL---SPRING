package com.booking_platform.infrastructure.persistence.repositoryJpa;

import com.booking_platform.infrastructure.persistence.entity.ChangeEmailCode;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ChangePasswordCodeJpaRepository extends JpaRepository<ChangeEmailCode, Long> {

    Optional<ChangeEmailCode> findByCode(Integer code);

    Optional<ChangeEmailCode> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByCode(Integer code);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM change_password_code c WHERE  c.expiration_date < :now")
    void deleteAllExpired(@Param("now") LocalDateTime now);
}
