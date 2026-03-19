package com.booking_platform.application.service.propertyServices;

import com.booking_platform.application.port.in.propertyUseCases.GetAllPropertiesByCountryUseCase;
import com.booking_platform.application.port.out.PropertyRepository;
import com.booking_platform.domain.model.Property;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class GetAllPropertiesByCountry implements GetAllPropertiesByCountryUseCase {

    private final PropertyRepository propertyRepository;

    public GetAllPropertiesByCountry(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public Page<Property> execute(int page, int size, String city) {
        return this.propertyRepository.findAllByCountry(page, size, city);
    }
}
