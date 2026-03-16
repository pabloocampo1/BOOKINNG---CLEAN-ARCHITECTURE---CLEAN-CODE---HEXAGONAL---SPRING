package com.booking_platform.infrastructure.persistence.repositoryJpa;

import com.booking_platform.infrastructure.persistence.entity.PropertyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PropertyJpaRepository extends JpaRepository<PropertyEntity, Long> {


    @Query(value = "SELECT * FROM property p WHERE LOWER(p.country) = LOWER(:country) AND p.is_published = true",
            nativeQuery = true)
    Page<PropertyEntity> findAllByCountry(Pageable pageable, @Param("country") String country);

    Page<PropertyEntity> findAllByLocationCityIgnoreCaseAndIsPublishedTrue(Pageable pageable, String city);


    Page<PropertyEntity> findAllByUserUserId(Pageable pageable, Long userId);

}
