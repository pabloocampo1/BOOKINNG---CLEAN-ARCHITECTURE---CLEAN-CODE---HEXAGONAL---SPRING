package com.booking_platform.application.service.propertyServices;

import com.booking_platform.application.port.in.propertyUseCases.CreateAvailabilityBlockUseCase;
import com.booking_platform.application.port.out.AvailabilityRepository;
import com.booking_platform.application.port.out.PropertyRepository;
import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.domain.exceptions.property.AvailabilityRangeNotAvailableException;
import com.booking_platform.domain.model.Availability;
import com.booking_platform.domain.model.Property;
import com.booking_platform.domain.model.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CreateAvailabilityBlockService implements CreateAvailabilityBlockUseCase {

    private final AvailabilityRepository availabilityRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    public CreateAvailabilityBlockService(AvailabilityRepository availabilityRepository, UserRepository userRepository, PropertyRepository propertyRepository) {
        this.availabilityRepository = availabilityRepository;
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    @Transactional
    public Availability execute(String currentUser, Availability availability) {

        User user = this.userRepository.findByUsername(currentUser)
                .orElseThrow(() -> new EntityNotFoundException("No se pudo realizar la accion."));

        Property property = this.propertyRepository.findById(availability.getPropertyId())
                .orElseThrow(() -> new EntityNotFoundException("Propiedad no encontrada."));

        property.checkOwnerProperty(user.getUserId());

        boolean overlaps = this.availabilityRepository.existsOverlappingRange(
                property.getPropertyId(),
                availability.getStartDate(),
                availability.getEndDate()
        );

        if (overlaps) {
            throw new AvailabilityRangeNotAvailableException();
        }

        return this.availabilityRepository.save(availability);
    }
}
