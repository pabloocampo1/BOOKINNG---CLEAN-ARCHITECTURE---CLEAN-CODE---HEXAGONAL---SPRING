package com.booking_platform.infrastructure.rest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CreateBookingWithPaymentDtoRequest(
		@NotNull @Valid BookingDtoRequest booking,
		@NotNull @Valid PaymentDtoRequest payment) {
}