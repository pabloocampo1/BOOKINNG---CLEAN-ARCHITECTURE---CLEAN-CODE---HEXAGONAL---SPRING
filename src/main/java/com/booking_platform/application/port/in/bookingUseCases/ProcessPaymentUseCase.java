package com.booking_platform.application.port.in.bookingUseCases;

import java.math.BigDecimal;

import com.booking_platform.domain.model.Booking;
import com.booking_platform.infrastructure.rest.dto.PaymentDtoRequest;

public interface ProcessPaymentUseCase {
	void execute(Booking booking, PaymentDtoRequest paymentDtoRequest, BigDecimal amount);
}
