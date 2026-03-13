package com.booking_platform.application.service.propertyServices;

import com.booking_platform.application.port.in.propertyUseCases.CreatePropertyUseCase;
import com.booking_platform.application.port.out.PropertyRepository;
import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.domain.exceptions.property.NoAccessToManageProperties;
import com.booking_platform.domain.model.Property;
import com.booking_platform.domain.model.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CreatePropertyService implements CreatePropertyUseCase {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public CreatePropertyService(PropertyRepository propertyRepository, UserRepository userRepository) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public Property execute(String owner, Property property) {

        property.validateInformation();

        User user = this.userRepository.findByUsername(owner).orElseThrow(() -> new EntityNotFoundException("Algo salio mal durante la creacion. intentalo de nuevo."));
        if(!user.canManageProperties()) throw  new NoAccessToManageProperties();

        property.setUserId(user.getUserId());

        return this.propertyRepository.save(property);
    }


}
