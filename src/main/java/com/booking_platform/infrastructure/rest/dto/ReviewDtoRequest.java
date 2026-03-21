package com.booking_platform.infrastructure.rest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReviewDtoRequest(
		@NotNull(message = "El ID de la propiedad es obligatorio") Long propertyId,

		@NotNull(message = "La calificación es obligatoria") @Min(value = 1, message = "La calificación debe ser al menos 1") @Max(value = 5, message = "La calificación debe ser como máximo 5") Integer rating,

		@NotBlank(message = "El comentario no puede estar vacío") @Size(max = 1000, message = "El comentario no puede tener más de 1000 caracteres") String comment) {
}