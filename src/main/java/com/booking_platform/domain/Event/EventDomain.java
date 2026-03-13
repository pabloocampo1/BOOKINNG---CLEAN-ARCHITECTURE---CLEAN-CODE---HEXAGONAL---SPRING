package com.booking_platform.domain.Event;

import java.time.LocalDateTime;

public interface EventDomain {
    LocalDateTime occurredOn();
    String typeEvent();
}
