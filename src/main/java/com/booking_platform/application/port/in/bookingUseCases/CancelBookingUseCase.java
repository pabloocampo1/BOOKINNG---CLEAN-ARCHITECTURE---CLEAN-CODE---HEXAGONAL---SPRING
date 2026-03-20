package com.booking_platform.application.port.in.bookingUseCases;

public interface CancelBookingUseCase {
	void execute(Long bookingId, String currentUser);
}
