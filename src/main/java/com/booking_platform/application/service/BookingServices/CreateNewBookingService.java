package com.booking_platform.application.service.BookingServices;

import org.springframework.stereotype.Service;

import com.booking_platform.application.port.in.bookingUseCases.CreateNewBookingUseCase;
import com.booking_platform.application.port.out.AvailabilityRepository;
import com.booking_platform.application.port.out.BookingRepository;

import com.booking_platform.domain.exceptions.booking.InvalidBookingDatesException;
import com.booking_platform.domain.model.Availability;
import com.booking_platform.domain.model.AvailabilityType;
import com.booking_platform.domain.model.Booking;

import com.booking_platform.domain.model.Property;
import com.booking_platform.domain.model.Role;
import com.booking_platform.domain.model.User;

import jakarta.transaction.Transactional;

@Service
public class CreateNewBookingService implements CreateNewBookingUseCase {

	private final BookingRepository bookingRepository;

	private final AvailabilityRepository availabilityRepository;

	public CreateNewBookingService(BookingRepository bookingRepository, AvailabilityRepository availabilityRepository) {
		this.bookingRepository = bookingRepository;

		this.availabilityRepository = availabilityRepository;

	}

	@Override
	@Transactional
	public Booking execute(Booking booking, Property property, User user) {

		// initialize booking and validate business rules
		this.validateUserRole(user, booking);
		booking.initializeBooking(property, user);

		// check availability and block dates
		this.checkAvailability(property, booking);
		property.checkIfCanBooking();
		Availability blockedAvailability = this.blockDateProperty(booking);

		// persist data
		this.availabilityRepository.save(blockedAvailability);
		Booking createdBooking = this.bookingRepository.save(booking);

		return createdBooking;

	}

	public void validateUserRole(User user, Booking booking) {

		if (!user.getRole().equals(Role.GUEST)) {
			throw new RuntimeException("User must have the role of GUEST to create a booking.");
		}
	}

	public Availability blockDateProperty(Booking booking) {

		Availability availability = new Availability();
		availability.setPropertyId(booking.getPropertyId());
		availability.setStartDate(booking.getCheckIn());
		availability.setEndDate(booking.getCheckOut());
		availability.setAvailabilityType(AvailabilityType.BOOKING);

		return availability;

	}

	public void checkAvailability(Property property, Booking booking) {

		if (this.availabilityRepository.existsOverlappingRange(property.getPropertyId(), booking.getCheckIn(),
				booking.getCheckOut())) {
			throw new InvalidBookingDatesException("La propiedad no esta disponible en ese rango de fechas.");

		}
	}

}
