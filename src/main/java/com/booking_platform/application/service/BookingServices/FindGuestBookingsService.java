package com.booking_platform.application.service.BookingServices;

import java.util.List;

import org.springframework.stereotype.Service;

import com.booking_platform.application.port.in.bookingUseCases.FindGuestBookingsUseCase;
import com.booking_platform.application.port.out.BookingRepository;
import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.domain.model.Booking;
import com.booking_platform.domain.model.Role;
import com.booking_platform.domain.model.User;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FindGuestBookingsService implements FindGuestBookingsUseCase {

	private final BookingRepository bookingRepository;
	private final UserRepository userRepository;

	public FindGuestBookingsService(BookingRepository bookingRepository, UserRepository userRepository) {
		this.bookingRepository = bookingRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<Booking> execute(String currentUser) {
		User user = this.userRepository.findByUsername(currentUser)
				.orElseThrow(() -> new EntityNotFoundException("User not found with username: " + currentUser));

		if (!user.getRole().equals(Role.GUEST)) {
			throw new IllegalStateException(
					"Solo los usuarios con rol de huésped pueden acceder a esta funcionalidad.");

		}

		List<Booking> bookings = this.bookingRepository.findByGuestId(user.getUserId());

		return bookings;
	}
}
