package com.booking_platform.infrastructure.rest.dto;

import java.time.LocalDate;

public record AvailabilityBlockDtoRequest(
        LocalDate startDate,
        LocalDate endDate
) {
}

