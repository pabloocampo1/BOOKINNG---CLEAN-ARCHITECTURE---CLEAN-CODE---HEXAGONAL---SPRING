package com.booking_platform.application.port.in.bookingUseCases;

public interface HostCancelBookingUseCase {
	void execute(Long bookingId, String currentUser);
}
