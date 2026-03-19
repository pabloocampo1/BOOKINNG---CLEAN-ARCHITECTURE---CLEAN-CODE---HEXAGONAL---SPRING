package com.booking_platform.application.port.in.bookingUseCases;

import com.booking_platform.domain.model.Booking;
import com.booking_platform.infrastructure.rest.dto.PaymentDtoRequest;

public interface ProcessBookingWithPaymentUseCase {
	Booking execute(Booking booking, PaymentDtoRequest paymentDtoRequest, String currentUser);
}
