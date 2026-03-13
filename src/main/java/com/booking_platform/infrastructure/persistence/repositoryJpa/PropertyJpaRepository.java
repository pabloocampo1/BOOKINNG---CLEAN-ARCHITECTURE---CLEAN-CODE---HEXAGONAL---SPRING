package com.booking_platform.infrastructure.persistence.repositoryJpa;

import com.booking_platform.infrastructure.persistence.entity.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyJpaRepository  extends JpaRepository<PropertyEntity, Long> {
}
