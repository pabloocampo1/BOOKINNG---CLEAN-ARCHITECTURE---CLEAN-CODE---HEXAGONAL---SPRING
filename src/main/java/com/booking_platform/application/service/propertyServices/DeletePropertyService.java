package com.booking_platform.application.service.propertyServices;

import com.booking_platform.application.port.in.propertyUseCases.DeletePropertyUseCase;
import com.booking_platform.application.port.out.PropertyRepository;
import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.domain.model.Property;
import com.booking_platform.domain.model.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DeletePropertyService implements DeletePropertyUseCase {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public DeletePropertyService(PropertyRepository propertyRepository, UserRepository userRepository) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void execute(Long propertyId, String currentUser) {

        Property property = this.propertyRepository.findById(propertyId)
                .orElseThrow(() -> new EntityNotFoundException("Error al intentar eliminar la propiedad."));


        User user = this.userRepository.findByUsername(currentUser)
                .orElseThrow(() -> new EntityNotFoundException("No se pudo realizar la accion."));


        property.checkOwnerProperty(user.getUserId());

        this.propertyRepository.deleteById(propertyId);
    }
}
