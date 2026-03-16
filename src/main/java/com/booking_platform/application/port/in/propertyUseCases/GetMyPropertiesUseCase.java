package com.booking_platform.application.port.in.propertyUseCases;

import com.booking_platform.domain.model.Property;
import org.springframework.data.domain.Page;

public interface GetMyPropertiesUseCase {
    Page<Property> execute(int page, int size, String currentUser);
}
