package com.booking_platform.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Booking {
    private final Long bookingId;
    private Long userId;
    private Long propertyId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private int totalGuests;
    private BigDecimal price;
    private StateBooking stateBooking;
    private LocalDateTime createAt;
    private LocalDateTime cancelledAt;


    public Booking(Long bookingId, Long userId, Long propertyId, LocalDateTime checkIn, LocalDateTime checkOut, int totalGuests, BigDecimal price, LocalDateTime createAt, LocalDateTime cancelledAt) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.propertyId = propertyId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalGuests = totalGuests;
        this.price = price;
        this.createAt = createAt;
        this.cancelledAt = cancelledAt;
        this.stateBooking = StateBooking.PENDING;
    }



}
