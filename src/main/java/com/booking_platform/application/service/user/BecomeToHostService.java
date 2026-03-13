package com.booking_platform.application.service.user;

import com.booking_platform.application.port.in.userUseCase.BecomeToHostUseCase;
import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.domain.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BecomeToHostService implements BecomeToHostUseCase {

    private final UserRepository userRepository;

    public BecomeToHostService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String execute(Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("No se  pudo realizar tu solicitud."));
        user.becomeHost();
        this.userRepository.save(user);
        return "Operacion exitosa, ahora tienes el rol de host.";
    }
}
