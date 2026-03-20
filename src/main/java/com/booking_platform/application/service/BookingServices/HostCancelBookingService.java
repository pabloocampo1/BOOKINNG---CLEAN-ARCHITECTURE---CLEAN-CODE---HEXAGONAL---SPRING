package com.booking_platform.application.service.BookingServices;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.booking_platform.application.port.in.bookingUseCases.HostCancelBookingUseCase;
import com.booking_platform.application.port.out.AvailabilityRepository;
import com.booking_platform.application.port.out.BookingRepository;
import com.booking_platform.application.port.out.PropertyRepository;
import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.domain.exceptions.booking.UnauthorizedBookingAccessException;
import com.booking_platform.domain.model.BookingStatus;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class HostCancelBookingService implements HostCancelBookingUseCase {
	private final BookingRepository bookingRepository;
	private final UserRepository userRepository;
	private final AvailabilityRepository availabilityRepository;
	private final PropertyRepository propertyRepository;

	public HostCancelBookingService(BookingRepository bookingRepository, UserRepository userRepository,
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

		if (!property.getUserId().equals(host.getUserId())) {
			throw new UnauthorizedBookingAccessException(
					"Solo el host dueño de esta propiedad puede cancelar la reserva con esta ruta.");
		}

		if (booking.getStatus() == BookingStatus.CANCELLED) {
			throw new IllegalStateException("Esta reserva ya ha sido cancelada previamente.");
		}

		this.restoreAvailabilityProperty(booking.getId());

		booking.setStatus(BookingStatus.CANCELLED);
		booking.setCancelledAt(LocalDateTime.now());
		this.bookingRepository.save(booking);

		var guest = this.userRepository.findById(booking.getGuestId())
				.orElseThrow(() -> new EntityNotFoundException("Guest user not found for refund."));

		System.out.println("Reembolso TOTAL para el huésped " + guest.getUsername() + " por la reserva "
				+ booking.getId() + ", monto: " + booking.getTotalPrice() + ".");
	}

	private void restoreAvailabilityProperty(Long bookingId) {
		this.availabilityRepository.deleteByBookingId(bookingId);
	}
}
