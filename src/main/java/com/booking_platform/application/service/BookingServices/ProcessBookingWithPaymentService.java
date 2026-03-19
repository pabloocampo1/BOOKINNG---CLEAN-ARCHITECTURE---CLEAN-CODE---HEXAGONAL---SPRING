package com.booking_platform.application.service.BookingServices;

import org.springframework.stereotype.Service;

import com.booking_platform.application.port.in.bookingUseCases.CreateNewBookingUseCase;
import com.booking_platform.application.port.in.bookingUseCases.ProcessBookingWithPaymentUseCase;
import com.booking_platform.application.port.in.bookingUseCases.ProcessPaymentUseCase;

import com.booking_platform.application.port.out.EventPublisher;
import com.booking_platform.application.port.out.PropertyRepository;
import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.domain.Event.CreateBookingEvent;
import com.booking_platform.domain.exceptions.DomainException;

import com.booking_platform.domain.model.Booking;
import com.booking_platform.domain.model.BookingStatus;
import com.booking_platform.domain.model.Property;
import com.booking_platform.domain.model.User;
import com.booking_platform.infrastructure.rest.dto.PaymentDtoRequest;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProcessBookingWithPaymentService implements ProcessBookingWithPaymentUseCase {

	private final EventPublisher eventPublisher;
	private final UserRepository userRepository;
	private final PropertyRepository propertyRepository;
	private final ProcessPaymentUseCase processPaymentUseCase;
	private final CreateNewBookingUseCase createNewBookingUseCase;
	private final SaveBooking saveBooking;

	public ProcessBookingWithPaymentService(EventPublisher eventPublisher, UserRepository userRepository,
			PropertyRepository propertyRepository, ProcessPaymentUseCase processPaymentUseCase,
			CreateNewBookingUseCase createNewBookingUseCase, SaveBooking saveBooking) {
		this.eventPublisher = eventPublisher;
		this.userRepository = userRepository;
		this.propertyRepository = propertyRepository;
		this.processPaymentUseCase = processPaymentUseCase;
		this.createNewBookingUseCase = createNewBookingUseCase;
		this.saveBooking = saveBooking;
	}

	@Override
	@Transactional
	public Booking execute(Booking booking, PaymentDtoRequest paymentDtoRequest, String currentUser) {

		Property property = propertyRepository.findById(booking.getPropertyId())
				.orElseThrow(
						() -> new EntityNotFoundException("Property not found with id: " + booking.getPropertyId()));

		User user = userRepository.findByUsername(currentUser)
				.orElseThrow(() -> new EntityNotFoundException(
						"No eres un usuario registrado. no puedes realizar esta accion. "));

		Booking createdBooking = this.createNewBookingUseCase.execute(booking, property, user);

		try {

			this.processPaymentUseCase.execute(createdBooking, paymentDtoRequest,
					createdBooking.getTotalPrice());

			createdBooking.setStatus(BookingStatus.CONFIRMED);

		} catch (DomainException e) {
			throw e;
		}

		this.sendEmalBookingCreated(createdBooking, property, user);

		return this.saveBooking.execute(createdBooking);
	}

	public void sendEmalBookingCreated(Booking booking, Property property, User user) {

		User host = userRepository.findById(property.getUserId())
				.orElseThrow(() -> new EntityNotFoundException("Host not found with id: " + property.getUserId()));

		CreateBookingEvent event = new CreateBookingEvent(
				booking.getId(),
				property.getPropertyId(),
				user.getUserId(),
				host.getUserId(),
				booking.getTotalPrice(),
				booking.getTotalNights().intValue(),
				property.getPropertyTypeName(),
				booking.getCheckIn(),
				booking.getCheckOut(),
				host.getEmail(),
				user.getEmail(),
				host.getFullName(),
				property.getFullAddress());

		this.eventPublisher.publish(event);

	}
}
