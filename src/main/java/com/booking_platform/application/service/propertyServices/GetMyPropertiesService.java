package com.booking_platform.application.service.propertyServices;

import com.booking_platform.application.port.in.propertyUseCases.GetMyPropertiesUseCase;
import com.booking_platform.application.port.out.PropertyRepository;
import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.domain.model.Property;
import com.booking_platform.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class GetMyPropertiesService implements GetMyPropertiesUseCase {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    public GetMyPropertiesService(UserRepository userRepository, PropertyRepository propertyRepository) {
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
    }


    @Override
    public Page<Property> execute(int page, int size, String currentUser) {
        User user = this.userRepository.findByUsername(currentUser).orElseThrow();
        return this.propertyRepository.findAllByUserUserId(page, size,user.getUserId());
    }
}
