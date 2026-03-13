package com.booking_platform.infrastructure.rest.dto;

import com.booking_platform.domain.model.Location;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record PropertyDtoRequest(
        String title,
        String description,
        String type,
        BigDecimal pricePerNight,
        int maxGuests,
        int bedrooms,
        int beds,
        int bathrooms,
        Location location,
        Set<String> amenities,
        List<String> houseRules,
        String cancellationPolicy,
        List<String> photos
) {
}
