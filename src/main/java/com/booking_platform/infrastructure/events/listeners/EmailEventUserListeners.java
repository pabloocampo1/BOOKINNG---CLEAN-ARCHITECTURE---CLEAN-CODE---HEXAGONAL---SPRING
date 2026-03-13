package com.booking_platform.infrastructure.events.listeners;

import com.booking_platform.domain.Event.SendTokenChangePasswordEvent;
import com.booking_platform.domain.Event.WelcomeUserEmailEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailEventUserListeners {

    private static final Logger log = LoggerFactory.getLogger(EmailEventUserListeners.class);


    @EventListener
    @Async
    public void welcomeUser(WelcomeUserEmailEvent event){
       log.info("simulado envio de emial al nuevo usuario : " +event.getName());
    }

    @EventListener
    @Async
    public void sendTokenChangePassword(SendTokenChangePasswordEvent event){
        log.info("Simulando envio del token a ");
        log.info(event.getEmail());
        log.info("Con el token: ");
        log.info(event.getToken().toString());
    }


}
