package com.booking_platform.infrastructure.rest.dto;

import com.booking_platform.domain.model.Location;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record PropertyDtoResponse(
        Long propertyId,
        String title,
        String description,
        String type,
        BigDecimal pricePerNight,
        Location location,
        Set<String> amenities,
        List<String> photos,
        String propertyStatus,
        Long userId
) {
}
