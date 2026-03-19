package com.booking_platform.application.port.in.bookingUseCases;

import com.booking_platform.domain.model.Booking;
import com.booking_platform.domain.model.Property;
import com.booking_platform.domain.model.User;

public interface CreateNewBookingUseCase {
	Booking execute(Booking booking, Property property, User user);
}
