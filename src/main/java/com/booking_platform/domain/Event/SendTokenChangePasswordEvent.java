package com.booking_platform.domain.Event;

import java.time.LocalDateTime;

public class SendTokenChangePasswordEvent implements EventDomain{

    private String email;
    private Integer token;
    private LocalDateTime createAt;

    public SendTokenChangePasswordEvent(String email, Integer token) {
        this.email = email;
        this.token = token;
        this.createAt = LocalDateTime.now();
    }


    public String getEmail() {
        return email;
    }

    public Integer getToken() {
        return token;
    }

    @Override
    public LocalDateTime occurredOn() {
        return this.createAt;
    }

    @Override
    public String typeEvent() {
        return "";
    }
}
