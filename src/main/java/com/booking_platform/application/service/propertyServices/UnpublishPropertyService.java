package com.booking_platform.application.service.propertyServices;

import com.booking_platform.application.port.in.propertyUseCases.UnpublishPropertyUseCase;
import com.booking_platform.application.port.out.PropertyRepository;
import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.domain.model.Property;
import com.booking_platform.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class UnpublishPropertyService implements UnpublishPropertyUseCase {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    public UnpublishPropertyService(UserRepository userRepository, PropertyRepository propertyRepository) {
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
    }


    @Override
    public void execute(Long propertyId, String currentUser) {
        User user = this.userRepository.findByUsername(currentUser).orElseThrow();
        Property property = this.propertyRepository.findById(propertyId)
                .orElseThrow();

        property.checkOwnerProperty(user.getUserId());
        property.unpublished();

        this.propertyRepository.save(property);
    }
}
