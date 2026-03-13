package com.booking_platform.domain.exceptions.property;

public class MaxGuestException extends RuntimeException {
    public MaxGuestException(int maxGues) {
        super("No puedes hospedar mas de" + " " + maxGues + " " + "personas en esta propiedad");
    }
}
