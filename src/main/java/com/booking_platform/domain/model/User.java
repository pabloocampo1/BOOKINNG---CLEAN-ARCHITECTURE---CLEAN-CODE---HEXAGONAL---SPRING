package com.booking_platform.domain.model;

import com.booking_platform.domain.exceptions.UserException.InvalidEmailException;
import com.booking_platform.domain.exceptions.UserException.PasswordUserException;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

public class User {
    private Long userId;
    private String name;
    private String lastName;
    private Role role;
    private String email;
    private Long dni;
    private LocalDateTime createAt;
    private String password;
    private String username;
    private boolean state;

    public User(String name, String lastName, Long dni, String email, LocalDateTime createAt, String password,
            String username) {
        this.validEmail(email);
        this.validatePassword(password);

        this.name = name;
        this.lastName = lastName;
        this.role = Role.GUEST;
        this.dni = dni;
        this.email = email;
        this.createAt = createAt;
        this.password = password;
        this.username = username;
        this.state = true;
    }

    public void validEmail(String email) {
        if (email.isBlank() || !email.contains("@")) {
            throw new InvalidEmailException();
        }
    }

    public void validatePassword(String password) {

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        if (password.length() <= 8) {
            throw new PasswordUserException("La contraseña es demaciado corta.");
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new PasswordUserException("La contraseña debe de tener almenos una mayuscula");
        }

        if (!password.matches(".*\\d.*")) {
            throw new PasswordUserException("La contraseña debe de contener almenos un numero");
        }

        if (!password.matches(".*[^a-zA-Z0-9].*")) {
            throw new PasswordUserException("La contraseña debe de contener almenos un simbolo");
        }

    }

    public String getFullName() {
        return this.name + " " + this.lastName;
    }

    public void active() {
        if (this.state) {
            return;
        }
        this.state = true;

    }

    public void inactive() {
        if (!this.state) {
            return;
        }
        this.state = false;

    }

    public boolean canManageProperties() {
        return role.equals(Role.HOST);
    }

    public void becomeHost() {
        if (!this.role.equals(Role.HOST))
            this.role = Role.HOST;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
