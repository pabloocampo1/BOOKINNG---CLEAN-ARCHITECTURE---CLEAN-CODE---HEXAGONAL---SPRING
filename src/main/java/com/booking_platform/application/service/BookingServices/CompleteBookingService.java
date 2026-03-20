package com.booking_platform.application.service.BookingServices;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.booking_platform.application.port.in.bookingUseCases.CompleteBookingUseCase;
import com.booking_platform.application.port.out.AvailabilityRepository;
import com.booking_platform.application.port.out.BookingRepository;
import com.booking_platform.application.port.out.PropertyRepository;
import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.domain.exceptions.booking.UnauthorizedBookingAccessException;
import com.booking_platform.domain.model.BookingStatus;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CompleteBookingService implements CompleteBookingUseCase {
	private final BookingRepository bookingRepository;
	private final UserRepository userRepository;
	private final AvailabilityRepository availabilityRepository;
	private final PropertyRepository propertyRepository;

	public CompleteBookingService(BookingRepository bookingRepository, UserRepository userRepository,
			AvailabilityRepository availabilityRepository, PropertyRepository propertyRepository) {
		this.bookingRepository = bookingRepository;
		this.userRepository = userRepository;
		this.availabilityRepository = availabilityRepository;
		this.propertyRepository = propertyRepository;
	}

	@Override
	@Transactional
	public void execute(Long bookingId, String currentUser) {

		var booking = this.bookingRepository.findById(bookingId)
				.orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + bookingId));

		var host = this.userRepository.findByUsername(currentUser)
				.orElseThrow(() -> new EntityNotFoundException("User not found with username: " + currentUser));

		var property = this.propertyRepository.findById(booking.getPropertyId())
				.orElseThrow(
						() -> new EntityNotFoundException("Property not found with id: " + booking.getPropertyId()));

		if (!Objects.equals(property.getUserId(), host.getUserId())) {
			throw new UnauthorizedBookingAccessException(
					"No tienes permisos para completar esta reserva. Solo el dueño puede usar este endpoint.");
		}

		if (booking.getStatus() == BookingStatus.COMPLETED || booking.getStatus() == BookingStatus.CANCELLED) {
			throw new IllegalStateException("Esta reserva ya ha sido completada previamente.");
		}

		booking.markAsCompleted();
		this.bookingRepository.save(booking);

		this.availabilityRepository.deleteByBookingId(booking.getId());

		// siluation of sent email or notification to guest about completed booking
		var guest = this.userRepository.findById(booking.getGuestId())
				.orElseThrow(() -> new EntityNotFoundException("Guest user not found for refund."));

		// siluation of sent email or notification to host about completed booking
		System.out.println("Reserva " + booking.getId() + " completada exitosamente. Notificación enviada al huésped "
				+ guest.getUsername() + " y al host " + host.getUsername() + ".");

	}

}
