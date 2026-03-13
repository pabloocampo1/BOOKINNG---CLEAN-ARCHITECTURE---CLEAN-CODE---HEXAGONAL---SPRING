package com.booking_platform.domain.model;

import com.booking_platform.domain.exceptions.TokenChangePasswordExpired;

import java.time.LocalDateTime;

import com.booking_platform.domain.Event.DeleteTokenChangeEmailEvent;

public class ChangeEmailCodeModel {

    private Long id;
    private Integer code;
    private String email;
    private LocalDateTime expirationDate;
    private LocalDateTime createdAt;


    public ChangeEmailCodeModel(Long id, Integer code, String email) {
        this.id = id;
        this.code = code;
        this.email = email;
        this.expirationDate = LocalDateTime.now().plusMinutes(2);
        this.createdAt = LocalDateTime.now();
    }

    public ChangeEmailCodeModel() {
        this.expirationDate = LocalDateTime.now().plusMinutes(2);
    }

    public void validateTokenExpiration(){
        if(this.expirationDate.isBefore(LocalDateTime.now())){

            throw new TokenChangePasswordExpired();
        }
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
