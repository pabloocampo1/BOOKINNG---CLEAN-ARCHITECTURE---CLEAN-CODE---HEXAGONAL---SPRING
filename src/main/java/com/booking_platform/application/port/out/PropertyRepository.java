package com.booking_platform.application.port.out;

import com.booking_platform.domain.model.Property;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository {

    Property save(Property property);
    List<Property> findAll();
    Optional<Property> findById(Long propertyId);
    Page<Property> findAllByCity(int page, int size , String city);
    Page<Property> findAllByCountry(int page, int size , String country);
    Page<Property> findAllPage(int page, int size );
    void deleteById(Long id);
    Page<Property> findAllByUserUserId(int page, int size , Long userId);

}          
