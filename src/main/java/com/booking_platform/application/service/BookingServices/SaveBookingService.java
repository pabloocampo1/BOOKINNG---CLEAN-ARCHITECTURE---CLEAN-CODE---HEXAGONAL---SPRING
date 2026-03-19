package com.booking_platform.application.service.BookingServices;

import org.springframework.stereotype.Service;

import com.booking_platform.application.port.out.BookingRepository;
import com.booking_platform.domain.model.Booking;

@Service
public class SaveBookingService implements SaveBooking {

	private final BookingRepository bookingRepository;

	public SaveBookingService(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	@Override
	public Booking execute(Booking booking) {

		return this.bookingRepository.save(booking);

	}

}
