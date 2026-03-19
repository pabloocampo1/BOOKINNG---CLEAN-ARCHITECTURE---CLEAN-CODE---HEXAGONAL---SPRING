package com.booking_platform.domain.Event;

import java.time.LocalDateTime;

public class DeleteTokenChangeEmailEvent implements EventDomain {

    private Long id;
    private LocalDateTime createdAt;

    public DeleteTokenChangeEmailEvent(Long id) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    @Override
    public LocalDateTime occurredOn() {
        return this.createdAt;
    }

    @Override
    public String typeEvent() {
        return "Delete token";
    }
}
