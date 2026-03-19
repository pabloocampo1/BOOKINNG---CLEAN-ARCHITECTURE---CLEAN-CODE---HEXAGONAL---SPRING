package com.booking_platform.domain.exceptions.booking;

import com.booking_platform.domain.exceptions.DomainException;

public class FailedPaymentException extends DomainException {
    public FailedPaymentException() {
        super("La transacción de pago falló. El booking ha sido cancelado.");
    }

    public FailedPaymentException(String message) {
        super(message);
    }
}
