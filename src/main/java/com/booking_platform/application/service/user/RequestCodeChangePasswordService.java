package com.booking_platform.application.service.user;

import com.booking_platform.application.port.in.userUseCase.RequestPasswordResetUseCase;
import com.booking_platform.application.port.out.ChangePasswordRepository;
import com.booking_platform.application.port.out.EventPublisher;
import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.domain.Event.SendTokenChangePasswordEvent;
import com.booking_platform.domain.exceptions.TokenChangePasswordExpired;
import com.booking_platform.domain.model.ChangeEmailCodeModel;
import com.booking_platform.domain.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;


import java.util.concurrent.ThreadLocalRandom;

@Service
/**
 * Application service responsible for handling the request to change/reset a user's password.
 * <p>
 * Main responsibilities:
 * - Validate that the user exists by email.
 * - Generate a unique, temporary numeric code for password change.
 * - Persist the generated code.
 * - Publish an event so that the code can be delivered to the user (e.g. via email).
 */
public class RequestCodeChangePasswordService implements RequestPasswordResetUseCase {

    private final ChangePasswordRepository changePasswordRepository;
    private final UserRepository userRepository;
    private final EventPublisher eventPublisher;

    public RequestCodeChangePasswordService(ChangePasswordRepository changePasswordRepository, UserRepository userRepository, EventPublisher eventPublisher) {
        this.changePasswordRepository = changePasswordRepository;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    /**
     * Orchestrates the password reset code request flow:
     * finds the user, generates a code, saves it and publishes an event to send it.
     */
    public Integer execute(String email) {

        User user = this.userRepository.findUserByEmail(email).
                orElseThrow(() -> new EntityNotFoundException("Ocurrio un error al intentar enviar el codigo, revisa tu email y vuelve a intentarlo."));

        ChangeEmailCodeModel existingCode = this.changePasswordRepository.findByEmail(user.getEmail()).orElse(null);

        if (existingCode != null) {
            try {
                existingCode.validateTokenExpiration();
                throw new IllegalStateException("Ya tienes un código activo para cambiar tu contraseña, revisa tu email.");
            } catch (TokenChangePasswordExpired ex) {
                this.changePasswordRepository.delete(existingCode.getId());
            }
        }

        Integer code = this.generateAndValidateCode();

        ChangeEmailCodeModel changeEmailCodeModel = new ChangeEmailCodeModel();
        changeEmailCodeModel.setCode(code);
        changeEmailCodeModel.setEmail(user.getEmail());

        // Persist the generated code so it can later be validated when the user submits it
        this.changePasswordRepository.save(changeEmailCodeModel);

        // Publish domain event so that an external handler can send the code (e.g. email notification)
        SendTokenChangePasswordEvent sendTokenChangePasswordEvent = new SendTokenChangePasswordEvent(user.getEmail(), code );

        this.eventPublisher.publish(sendTokenChangePasswordEvent);
        return code;
    }


    /**
     * Generates a numeric code and ensures that it does not already exist in the persistence layer,
     * trying until a unique code is found.
     */
    public Integer generateAndValidateCode(){
        Integer code = 0;
        boolean isCodeValid = false;

        // Loop until a code is generated that does not already exist in the repository
        while(!isCodeValid){
            Integer codeGenerated = this.generateCode();
            if(!this.validateCode(codeGenerated)){

                code = codeGenerated;
                isCodeValid = true;
            }


        }

        return code;
    }

    /**
     * Generates a random 4-digit integer between 1000 (inclusive) and 10000 (exclusive).
     */
    public int generateCode(){
        return ThreadLocalRandom.current().nextInt(1000, 10000);
    }

    /**
     * Checks whether the given code already exists in the persistence layer.
     * The method name can be read as "code is valid if it does NOT exist".
     */
    public boolean validateCode(Integer code){
        return  this.changePasswordRepository.existCode(code);
    }







}
