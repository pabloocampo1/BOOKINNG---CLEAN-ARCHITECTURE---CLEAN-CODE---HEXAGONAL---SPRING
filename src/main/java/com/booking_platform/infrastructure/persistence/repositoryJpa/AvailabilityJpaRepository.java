package com.booking_platform.infrastructure.persistence.repositoryJpa;

import com.booking_platform.infrastructure.persistence.entity.AvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityJpaRepository extends JpaRepository<AvailabilityEntity, Long> {

    List<AvailabilityEntity> findAllByPropertyPropertyId(Long propertyId);

    @Query("""
            select (count(a) > 0) from AvailabilityEntity a
            where a.property.propertyId = :propertyId
              and a.startDate <= :endDate
              and a.endDate >= :startDate
            """)
    boolean existsOverlappingRange(
            @Param("propertyId") Long propertyId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}

