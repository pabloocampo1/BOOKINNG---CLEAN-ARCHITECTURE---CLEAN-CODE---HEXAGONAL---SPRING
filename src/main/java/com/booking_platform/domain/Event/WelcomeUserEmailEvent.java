package com.booking_platform.domain.Event;

import java.time.LocalDateTime;

public class WelcomeUserEmailEvent implements EventDomain {

    private final LocalDateTime occurredOn;
    private final String email;
    private final String name;

    public WelcomeUserEmailEvent(String email, String name) {

        this.occurredOn = LocalDateTime.now();
        this.email = email;
        this.name = name;
    }

    @Override
    public LocalDateTime occurredOn() {
        return this.occurredOn;
    }

    @Override
    public String typeEvent() {
        return "WelcomeUser";
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
