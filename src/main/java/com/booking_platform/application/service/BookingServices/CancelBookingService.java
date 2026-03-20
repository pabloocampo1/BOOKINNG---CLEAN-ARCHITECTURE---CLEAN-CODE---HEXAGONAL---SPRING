package com.booking_platform.application.service.BookingServices;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.booking_platform.application.port.in.bookingUseCases.CancelBookingUseCase;
import com.booking_platform.application.port.out.AvailabilityRepository;
import com.booking_platform.application.port.out.BookingRepository;
import com.booking_platform.application.port.out.PropertyRepository;
import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.domain.exceptions.booking.UnauthorizedBookingAccessException;
import com.booking_platform.domain.model.Booking;
import com.booking_platform.domain.model.BookingStatus;
import com.booking_platform.domain.model.Property;
import com.booking_platform.domain.model.User;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CancelBookingService implements CancelBookingUseCase {
	private final BookingRepository bookingRepository;
	private final UserRepository userRepository;
	private final AvailabilityRepository availabilityRepository;
	private final PropertyRepository propertyRepository;

	public CancelBookingService(BookingRepository bookingRepository, UserRepository userRepository,
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

		var user = this.userRepository.findByUsername(currentUser)
				.orElseThrow(() -> new EntityNotFoundException("User not found with username: " + currentUser));

		var property = this.propertyRepository.findById(booking.getPropertyId())
				.orElseThrow(
						() -> new EntityNotFoundException("Property not found with id: " + booking.getPropertyId()));

		if (booking.getGuestId() != user.getUserId()) {
			throw new UnauthorizedBookingAccessException();

		}

		if (booking.getStatus() == BookingStatus.CANCELLED) {
			throw new IllegalStateException("Esta reserva ya ha sido cancelada previamente.");
		}

		this.restoreAvailabilityProperty(booking.getId());

		booking.setStatus(BookingStatus.CANCELLED);
		booking.setCancelledAt(LocalDateTime.now());

		this.bookingRepository.save(booking);

		this.applyCancellationPolicy(property, user, booking);

	}

	private void restoreAvailabilityProperty(Long bookingId) {
		this.availabilityRepository.deleteByBookingId(bookingId);
	}

	public void applyCancellationPolicy(Property property, User user, Booking booking) {

		// simulacion de reembolso segun politica de cancelacion

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime createdAt = booking.getCreatedAt();

		Duration duration = Duration.between(createdAt, now);

		switch (property.getCancellationPolicy()) {
			case FLEXIBLE:
				if (duration.toHours() < 24) {
					System.out.println("Reembolso completo para el usuario: " + user.getUsername()
							+ " por la reserva con id: " + booking.getId() + " a las: " + LocalDateTime.now()
							+ " por haber cancelado antes de las 24 horas de haber hecho la reservacion: ");
				}
				break;
			case MODERATE:
				if (duration.toDays() < 5) {
					System.out.println("Reembolso del %50 para el usuario: " + user.getUsername()
							+ " por la reserva con id: " + booking.getId() + " a las: " + LocalDateTime.now()
							+ " por haber cancelado antes de las 5 dias de haber hecho la reservacion: ");
				}
				break;
			case STRICT:
				System.out.println("No hay reembolso para el usuario: " + user.getUsername());
				break;
		}
	}

}
