package com.booking_platform.infrastructure.rest.dto;

import java.time.LocalDateTime;

public record ReviewDtoResponse(
		Long id,
		Long propertyId,
		Long guestId,
		String guestName,
		Integer rating,
		String comment,
		LocalDateTime createdAt) {
}