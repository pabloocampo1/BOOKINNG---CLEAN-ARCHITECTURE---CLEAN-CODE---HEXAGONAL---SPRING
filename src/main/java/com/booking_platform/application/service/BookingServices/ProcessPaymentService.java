package com.booking_platform.application.service.BookingServices;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.booking_platform.application.port.in.bookingUseCases.ProcessPaymentUseCase;
import com.booking_platform.domain.exceptions.booking.FailedPaymentException;
import com.booking_platform.domain.model.Booking;

import com.booking_platform.infrastructure.rest.dto.PaymentDtoRequest;

@Service
public class ProcessPaymentService implements ProcessPaymentUseCase {

	@Override
	public void execute(Booking booking, PaymentDtoRequest paymentDtoRequest, BigDecimal amount) {

		// Aquí iría la lógica real de procesamiento de pagos, como llamar a un servicio
		// de pago externo.
		// Por simplicidad, vamos a simular un pago exitoso si el número de tarjeta
		// termina en un dígito par, y un pago fallido si termina en un dígito impar.

		if (!paymentDtoRequest.cardNumber().endsWith("0") &&
				!paymentDtoRequest.cardNumber().endsWith("2") &&
				!paymentDtoRequest.cardNumber().endsWith("4") &&
				!paymentDtoRequest.cardNumber().endsWith("6") &&
				!paymentDtoRequest.cardNumber().endsWith("8")) {

			throw new FailedPaymentException();
		} else {
			System.out.println("Se realizo un pago de amount: " + amount + " para la reserva con id: " + booking.getId()
					+ " a las: " + LocalDateTime.now());
		}

	}
}
