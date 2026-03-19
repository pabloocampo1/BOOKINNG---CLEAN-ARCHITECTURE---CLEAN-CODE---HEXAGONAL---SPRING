package com.booking_platform.infrastructure.exception.handler;

import com.booking_platform.domain.exceptions.TokenChangePasswordExpired;
import com.booking_platform.domain.exceptions.UserException.InvalidEmailException;
import com.booking_platform.domain.exceptions.UserException.NoPermissionManagePropertyException;
import com.booking_platform.domain.exceptions.UserException.PasswordUserException;
import com.booking_platform.domain.exceptions.booking.InvalidBookingDatesException;
import com.booking_platform.domain.exceptions.booking.FailedPaymentException;
import com.booking_platform.domain.exceptions.booking.LimitGuestsException;
import com.booking_platform.domain.exceptions.property.AvailabilityRangeNotAvailableException;
import com.booking_platform.domain.exceptions.property.InvalidLocationException;
import com.booking_platform.domain.exceptions.property.NoAccessToManageProperties;
import com.booking_platform.infrastructure.exception.ErrorResponse;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.coyote.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception e) {

        e.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setError("Internal Server Error");
        errorResponse.setMessage(
                "Ups! Ha ocurrido un error inesperado en nuestros servidores. Por favor, inténtalo más tarde.");
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleElementsNotFound(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setError("Not found.");
        errorResponse.setMessage(e.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            InvalidLocationException.class,
            PasswordUserException.class,
            NoAccessToManageProperties.class,
            InvalidEmailException.class,
            AvailabilityRangeNotAvailableException.class,
            InvalidBookingDatesException.class,
            TokenChangePasswordExpired.class,
            LimitGuestsException.class,
            FailedPaymentException.class,
            NoPermissionManagePropertyException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception e) {
        Throwable actualException = (e.getCause() != null && e instanceof RuntimeException)
                ? e.getCause()
                : e;

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setError("Bad Request");
        errorResponse.setMessage(actualException.getMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException e) {

        String customMessage = "El recurso que intentas crear ya existe (DNI o Email duplicado).";

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.CONFLICT.value());
        errorResponse.setError("Conflict");
        errorResponse.setMessage(customMessage);

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setError("Inautorizado");
        errorResponse.setMessage("Credenciales invalidas");

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError("Bad request");
        errorResponse.setMessage(e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleReadableException(HttpMessageNotReadableException e) {
        Throwable mostSpecificCause = e.getMostSpecificCause();
        String message = mostSpecificCause.getMessage();

        // Limpiamos el mensaje si Jackson le agrega texto técnico extra
        if (message != null && message.contains(":")) {
            // A veces Jackson pone "Cannot construct instance... problem: El pais..."
            // Esto saca solo lo que está después del último ":"
            message = message.substring(message.lastIndexOf(":") + 1).trim();
        }

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setError("Bad Request - Input Error");
        errorResponse.setMessage(message);
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError("Validation Error");
        errorResponse.setMessage(message.isEmpty() ? "Validation failed" : message);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException e) {
        String message = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError("Validation Error");
        errorResponse.setMessage(message.isEmpty() ? "Constraint violation" : message);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
