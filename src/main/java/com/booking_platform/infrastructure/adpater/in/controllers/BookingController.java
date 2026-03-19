package com.booking_platform.infrastructure.adpater.in.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking_platform.application.port.in.bookingUseCases.ProcessBookingWithPaymentUseCase;
import com.booking_platform.infrastructure.rest.dto.BookingDtoResponse;
import com.booking_platform.infrastructure.rest.dto.CreateBookingWithPaymentDtoRequest;
import com.booking_platform.infrastructure.rest.mapperRest.BookingMapperRest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

	private final ProcessBookingWithPaymentUseCase ProcessBookingWithPaymentUseCase;
	private final BookingMapperRest bookingMapperRest;

	public BookingController(ProcessBookingWithPaymentUseCase ProcessBookingWithPaymentUseCase,
			BookingMapperRest bookingMapperRest) {
		this.ProcessBookingWithPaymentUseCase = ProcessBookingWithPaymentUseCase;
		this.bookingMapperRest = bookingMapperRest;
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

}
