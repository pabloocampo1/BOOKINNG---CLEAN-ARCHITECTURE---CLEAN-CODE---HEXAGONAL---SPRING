package com.booking_platform.application.port.in.bookingUseCases;

public interface CompleteBookingUseCase {
	void execute(Long bookingId, String currentUser);
}
