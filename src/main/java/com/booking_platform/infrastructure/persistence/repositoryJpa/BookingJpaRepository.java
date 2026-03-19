package com.booking_platform.infrastructure.persistence.repositoryJpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking_platform.infrastructure.persistence.entity.BookingEntity;

public interface BookingJpaRepository extends JpaRepository<BookingEntity, Long> {

}
