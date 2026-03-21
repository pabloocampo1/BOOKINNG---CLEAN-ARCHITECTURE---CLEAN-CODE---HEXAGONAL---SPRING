package com.booking_platform.infrastructure.persistence.repositoryJpa;

import com.booking_platform.infrastructure.persistence.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {
	List<ReviewEntity> findByProperty_PropertyId(Long propertyId);

	boolean existsByProperty_PropertyIdAndGuest_UserId(Long propertyId, Long guestId);
}