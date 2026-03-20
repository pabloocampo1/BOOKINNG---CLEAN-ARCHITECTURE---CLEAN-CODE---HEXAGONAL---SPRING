package com.booking_platform.infrastructure.adpater.in.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking_platform.application.port.in.bookingUseCases.CompleteBookingUseCase;
import com.booking_platform.application.port.in.bookingUseCases.HostCancelBookingUseCase;
import com.booking_platform.application.port.in.bookingUseCases.ProcessBookingWithPaymentUseCase;
import com.booking_platform.application.service.BookingServices.CancelBookingService;
import com.booking_platform.infrastructure.rest.dto.BookingDtoResponse;
import com.booking_platform.infrastructure.rest.dto.CreateBookingWithPaymentDtoRequest;
import com.booking_platform.infrastructure.rest.mapperRest.BookingMapperRest;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

	private final ProcessBookingWithPaymentUseCase ProcessBookingWithPaymentUseCase;
	private final BookingMapperRest bookingMapperRest;
	private final CancelBookingService cancelBookingService;
	private final HostCancelBookingUseCase hostCancelBookingUseCase;
	private final CompleteBookingUseCase completeBooking;

	public BookingController(ProcessBookingWithPaymentUseCase ProcessBookingWithPaymentUseCase,
			BookingMapperRest bookingMapperRest, CancelBookingService cancelBookingService,
			HostCancelBookingUseCase hostCancelBookingUseCase, CompleteBookingUseCase completeBooking) {
		this.ProcessBookingWithPaymentUseCase = ProcessBookingWithPaymentUseCase;
		this.bookingMapperRest = bookingMapperRest;
		this.cancelBookingService = cancelBookingService;
		this.hostCancelBookingUseCase = hostCancelBookingUseCase;
		this.completeBooking = completeBooking;
	}

	@PostMapping
	public ResponseEntity<BookingDtoResponse> createNewBooking(
			@Valid @RequestBody CreateBookingWithPaymentDtoRequest entity,
			Authentication authentication) {

		BookingDtoResponse bookingDtoResponse = this.bookingMapperRest
				.toResponse(
						this.ProcessBookingWithPaymentUseCase.execute(this.bookingMapperRest.toModel(entity.booking()),
								entity.payment(), authentication.getName()));

		return new ResponseEntity<>(bookingDtoResponse, HttpStatus.CREATED);
	}

	@PutMapping("/{bookingId}/cancel")
	public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId, Authentication authentication) {
		String currentUser = authentication.getName();
		this.cancelBookingService.execute(bookingId, currentUser);

		return new ResponseEntity<>("Reserva cancelada exitosamente", HttpStatus.OK);
	}

	@PutMapping("/{bookingId}/host-cancel")
	public ResponseEntity<String> hostCancelBooking(@PathVariable Long bookingId, Authentication authentication) {
		String currentUser = authentication.getName();
		this.hostCancelBookingUseCase.execute(bookingId, currentUser);

		return new ResponseEntity<>("Reserva cancelada por el host y reembolso aplicado", HttpStatus.OK);
	}

	@PutMapping("/{bookingId}/complete")
	public ResponseEntity<String> completeBooking(@PathVariable Long bookingId, Authentication authentication) {
		String currentUser = authentication.getName();
		this.completeBooking.execute(bookingId, currentUser);

		return new ResponseEntity<>("Reserva marcada como completada", HttpStatus.OK);
	}

}
