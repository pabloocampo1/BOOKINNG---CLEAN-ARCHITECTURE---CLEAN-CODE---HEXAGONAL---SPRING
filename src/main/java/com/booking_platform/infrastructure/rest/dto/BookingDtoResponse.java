package com.booking_platform.infrastructure.rest.dto;

import com.booking_platform.domain.model.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record BookingDtoResponse(
		Long id,
		Long propertyId,
		Long guestId,
		LocalDate checkIn,
		LocalDate checkOut,
		int numberOfGuests,
		BigDecimal totalPrice,
		BookingStatus status,
		LocalDateTime createdAt,
		LocalDateTime cancelledAt) {
}