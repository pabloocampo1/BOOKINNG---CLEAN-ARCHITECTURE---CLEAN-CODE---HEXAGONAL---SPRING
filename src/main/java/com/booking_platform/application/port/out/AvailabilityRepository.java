package com.booking_platform.application.port.out;

import com.booking_platform.domain.model.Availability;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AvailabilityRepository {

    Availability save(Availability availability);

    Optional<Availability> findById(Long id);

    List<Availability> findAllByPropertyId(Long propertyId);

    boolean existsOverlappingRange(Long propertyId, LocalDate startDate, LocalDate endDate);

    void deleteById(Long id);
}

