package com.booking_platform.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PaymentDtoRequest(@NotBlank(message = "El método de pago es obligatorio") String paymentMethod,

		@NotBlank(message = "El número de tarjeta es obligatorio") @Size(min = 13, max = 19, message = "Número de tarjeta inválido") @Pattern(regexp = "\\d+", message = "El número de tarjeta debe contener solo dígitos") String cardNumber,

		@NotBlank(message = "El nombre del titular es obligatorio") String cardHolderName,

		@NotBlank(message = "La fecha de expiración es obligatoria") @Pattern(regexp = "(0[1-9]|1[0-2])/[0-9]{2}", message = "Formato de fecha debe ser MM/YY") String expirationDate,

		@NotBlank(message = "El CVV es obligatorio") @Size(min = 3, max = 4, message = "El CVV debe tener 3 o 4 dígitos") @Pattern(regexp = "\\d+", message = "El CVV debe ser numérico") String cvv,

		@NotBlank(message = "La moneda es obligatoria") @Size(min = 3, max = 3, message = "La moneda debe tener 3 caracteres (ej. USD)") String currency) {

}
