package com.booking_platform.application.port.in.propertyUseCases;

import com.booking_platform.domain.model.Property;

public interface CreatePropertyUseCase {
    Property execute(String owner, Property property);
}
