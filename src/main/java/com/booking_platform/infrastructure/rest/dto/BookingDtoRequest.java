package com.booking_platform.infrastructure.rest.dto;

import java.time.LocalDate;

public record BookingDtoRequest(
		Long propertyId,
		LocalDate checkIn,
		LocalDate checkOut,
		int numberOfGuests) {
}