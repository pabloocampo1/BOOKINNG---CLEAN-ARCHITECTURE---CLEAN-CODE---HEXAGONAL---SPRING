package com.booking_platform.application.service.propertyServices;

import com.booking_platform.application.port.in.propertyUseCases.PublishPropertyUseCase;
import com.booking_platform.application.port.out.PropertyRepository;
import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.domain.model.Property;
import com.booking_platform.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class publishPropertyService implements PublishPropertyUseCase {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public publishPropertyService(PropertyRepository propertyRepository, UserRepository userRepository) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void execute(Long propertyId, String currentUser) {

        User user = this.userRepository.findByUsername(currentUser).orElseThrow();
        Property property = this.propertyRepository.findById(propertyId)
                .orElseThrow();

        property.checkOwnerProperty(user.getUserId());
        property.published();

        this.propertyRepository.save(property);
    }



}
