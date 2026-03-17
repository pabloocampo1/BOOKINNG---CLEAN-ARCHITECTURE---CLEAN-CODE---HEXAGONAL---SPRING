package com.booking_platform.application.port.in.propertyUseCases;

import com.booking_platform.domain.model.Availability;

public interface CreateAvailabilityBlockUseCase {
    Availability execute(String currentUser, Availability availability);
}

