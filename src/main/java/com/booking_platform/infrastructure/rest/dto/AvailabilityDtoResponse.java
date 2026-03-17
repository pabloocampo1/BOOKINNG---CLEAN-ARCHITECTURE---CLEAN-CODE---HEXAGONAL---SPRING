package com.booking_platform.infrastructure.rest.dto;

import java.time.LocalDate;

public record AvailabilityDtoResponse(
        Long id,
        Long propertyId,
        LocalDate startDate,
        LocalDate endDate,
        String availabilityType
) {
}

