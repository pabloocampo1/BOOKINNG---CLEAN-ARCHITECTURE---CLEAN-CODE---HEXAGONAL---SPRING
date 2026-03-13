package com.booking_platform.application.port.out;

import com.booking_platform.domain.model.Property;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository {

    Property save(Property property);
    List<Property> findAll();
    Optional<Property> findById(Long propertyId);

}          
