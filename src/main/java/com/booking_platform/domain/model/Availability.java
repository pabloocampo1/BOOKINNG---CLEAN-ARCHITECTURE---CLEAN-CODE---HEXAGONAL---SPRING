package com.booking_platform.domain.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Availability {
    private Long id;
    private Long propertyId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long bookingId;
    private AvailabilityType availabilityType; // BLOCKED OR BOOKING

    public Availability(Long id, Long propertyId, LocalDate startDate, LocalDate endDate,
            AvailabilityType availabilityType, Long bookingId) {
        this.id = id;
        this.propertyId = propertyId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.availabilityType = availabilityType;
        this.bookingId = bookingId;
    }

    public Availability() {
    }

    public Long getTotalNights() {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    public boolean isValidDate() {
        LocalDate today = LocalDate.now();

        if (this.startDate.isBefore(today)) {
            return false;
        }

        return !this.endDate.isBefore(this.startDate);
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

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public AvailabilityType getAvailabilityType() {
        return availabilityType;
    }

    public void setAvailabilityType(AvailabilityType availabilityType) {
        this.availabilityType = availabilityType;
    }
}
