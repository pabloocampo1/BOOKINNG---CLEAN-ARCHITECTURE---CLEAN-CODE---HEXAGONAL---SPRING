package com.booking_platform.application.service.propertyServices;

import com.booking_platform.application.port.in.propertyUseCases.GetAllPropertiesByCityUseCase;
import com.booking_platform.application.port.out.PropertyRepository;
import com.booking_platform.domain.model.Property;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class GetAllPropertiesByCity implements GetAllPropertiesByCityUseCase {

    private final PropertyRepository propertyRepository;


    public GetAllPropertiesByCity(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public Page<Property> execute(int page, int size, String city) {
        return propertyRepository.findAllByCity(page, size, city);
    }
}
