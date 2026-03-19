package com.booking_platform.application.service.BookingServices;

import com.booking_platform.domain.model.Booking;

public interface SaveBooking {
	Booking execute(Booking booking);
}
