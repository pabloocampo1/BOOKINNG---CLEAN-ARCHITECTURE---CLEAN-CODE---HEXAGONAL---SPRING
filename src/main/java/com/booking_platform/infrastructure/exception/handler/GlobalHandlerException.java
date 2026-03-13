package com.booking_platform.infrastructure.exception.handler;
import com.booking_platform.domain.exceptions.TokenChangePasswordExpired;
import com.booking_platform.domain.exceptions.UserException.InvalidEmailException;
import com.booking_platform.domain.exceptions.UserException.PasswordUserException;
import com.booking_platform.domain.exceptions.property.InvalidLocationException;
import com.booking_platform.domain.exceptions.property.NoAccessToManageProperties;
import com.booking_platform.infrastructure.exception.ErrorResponse;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;



@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception e) {

        e.printStackTrace();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .error("Internal Server Error")
                .message("Ups! Ha ocurrido un error inesperado en nuestros servidores. Por favor, inténtalo más tarde.")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(
            EntityNotFoundException.class
    )
    public ResponseEntity<ErrorResponse> handleElementsNotFound(Exception e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .error("Not found.")
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();


        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({
            InvalidLocationException.class,
            PasswordUserException.class,
            NoAccessToManageProperties.class,
            InvalidEmailException.class,
            TokenChangePasswordExpired.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception e){
        Throwable actualException = (e.getCause() != null && e instanceof RuntimeException)
                ? e.getCause() : e;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .error("Bad Request")
                .message(actualException.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException e) {
        // Aquí puedes lógica para revisar si el mensaje contiene "Duplicate entry"
        String customMessage = "El recurso que intentas crear ya existe (DNI o Email duplicado).";

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("Conflict")
              
                .message(customMessage)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse>  handleAuthenticationException(AuthenticationException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("Inautorizado")
                .message("Credenciales invalidas")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad request")
                .message(e.getMessage())
                .build();

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

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .error("Bad Request - Input Error")
                .message(message)
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
