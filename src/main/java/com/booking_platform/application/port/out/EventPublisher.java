package com.booking_platform.application.port.out;

import com.booking_platform.domain.Event.EventDomain;

public interface EventPublisher {
    void publish(EventDomain event);
}
