package com.booking_platform.infrastructure.events;

import com.booking_platform.application.port.out.EventPublisher;
import com.booking_platform.domain.Event.EventDomain;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.logging.Logger;

@Component

public class SpringEventPublisher implements EventPublisher {


    private final ApplicationEventPublisher publisher;

    public SpringEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }


    @Override
    public void publish(EventDomain event){

        publisher.publishEvent(event);
    }



}
