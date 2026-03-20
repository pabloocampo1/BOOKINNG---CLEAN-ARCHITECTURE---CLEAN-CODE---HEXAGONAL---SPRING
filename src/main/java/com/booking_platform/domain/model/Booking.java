package com.booking_platform.domain.model;

import com.booking_platform.domain.exceptions.booking.InvalidBookingDatesException;
import com.booking_platform.domain.exceptions.booking.LimitGuestsException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Booking {

    private Long id;

    private Long propertyId;
    private Long guestId;

    // Dates
    private LocalDate checkIn;
    private LocalDate checkOut;

    // Booking details
    private int numberOfGuests;
    private BigDecimal totalPrice;
    private BookingStatus status;

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime cancelledAt;

    public Booking(
            Long id,
            Long propertyId,
            Long guestId,
            LocalDate checkIn,
            LocalDate checkOut,
            int numberOfGuests,
            BigDecimal totalPrice,
            BookingStatus status,
            LocalDateTime createdAt,
            LocalDateTime cancelledAt) {
        this.id = id;
        this.propertyId = propertyId;
        this.guestId = guestId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numberOfGuests = numberOfGuests;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.cancelledAt = cancelledAt;
    }

    public void validateCheckInCheckOut() {

        if (this.checkIn == null || this.checkOut == null) {
            throw new InvalidBookingDatesException("Check-in y check-out son requeridos.");
        }

        if (!this.checkIn.isBefore(this.checkOut)) {
            throw new InvalidBookingDatesException("El check-in debe ser antes del check-out.");
        }

        LocalDate today = LocalDate.now();
        if (!this.checkIn.isAfter(today)) {
            throw new InvalidBookingDatesException("La fecha de check in debe de ser despues de hoy");
        }

    }

    public void initializeBooking(Property property, User user) {
        this.validateCheckInCheckOut();
        this.validateNumberOfGuests(property.getMaxGuests());
        this.totalPrice = this.calculateTotalPrice(property.getPricePerNight());
        this.status = BookingStatus.PENDING;
        this.guestId = user.getUserId();
        this.propertyId = property.getPropertyId();
    }

    public void validateNumberOfGuests(int propertyLimitOfGuests) {
        if (this.numberOfGuests <= 0) {
            throw new LimitGuestsException("El número de huéspedes debe ser mayor a cero.");
        }
        if (this.numberOfGuests > propertyLimitOfGuests) {
            throw new LimitGuestsException("El número de huéspedes excede la capacidad de la propiedad.");
        }

    }

    public BigDecimal calculateTotalPrice(BigDecimal pricePerNight) {
        long nights = java.time.temporal.ChronoUnit.DAYS.between(this.checkIn, this.checkOut);
        return pricePerNight.multiply(BigDecimal.valueOf(nights));
    }

    public Long getTotalNights() {
        return java.time.temporal.ChronoUnit.DAYS.between(this.checkIn, this.checkOut);
    }

    public boolean isReadyToBeCompleted() {
        LocalDate today = LocalDate.now();

        return !today.isBefore(this.checkOut);
    }

    public void markAsCompleted() {
        if (!isReadyToBeCompleted()) {
            throw new IllegalStateException("No se puede completar la reserva antes del: " + this.checkOut);
        }
        this.status = BookingStatus.COMPLETED;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(LocalDateTime cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

}
