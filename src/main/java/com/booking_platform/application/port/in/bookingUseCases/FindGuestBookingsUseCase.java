package com.booking_platform.application.port.in.bookingUseCases;

import java.util.List;

import com.booking_platform.domain.model.Booking;

public interface FindGuestBookingsUseCase {
	List<Booking> execute(String currentUser);

}
