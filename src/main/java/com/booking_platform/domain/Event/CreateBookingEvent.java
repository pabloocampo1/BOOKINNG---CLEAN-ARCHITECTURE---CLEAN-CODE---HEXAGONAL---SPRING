package com.booking_platform.domain.Event;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreateBookingEvent implements EventDomain {
	private Long bookingId;
	private Long propertyId;
	private Long guestId;
	private Long hostId;
	private BigDecimal totalPrice;
	private int totalNights;
	private String propertyType;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private String hostEmail;
	private String guestEmail;
	private String hostName;
	private String address;

	public CreateBookingEvent(Long bookingId, Long propertyId, Long guestId, Long hostId,
			BigDecimal totalPrice, int totalNights, String propertyType, LocalDate checkIn, LocalDate checkOut,
			String hostEmail, String guestEmail, String hostName, String address) {
		this.bookingId = bookingId;
		this.propertyId = propertyId;
		this.guestId = guestId;
		this.hostId = hostId;
		this.totalPrice = totalPrice;
		this.totalNights = totalNights;
		this.propertyType = propertyType;

		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.hostEmail = hostEmail;
		this.guestEmail = guestEmail;
		this.hostName = hostName;
		this.address = address;
	}

	@Override
	public String typeEvent() {
		return "SendNotificationCreateBooking";
	}

	@Override
	public LocalDateTime occurredOn() {
		return LocalDateTime.now();
	}

	public Long getBookingId() {
		return bookingId;
	}

	public Long getPropertyId() {
		return propertyId;
	}

	public Long getGuestId() {
		return guestId;
	}

	public Long getHostId() {
		return hostId;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public int getTotalNights() {
		return totalNights;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public LocalDate getCheckIn() {
		return checkIn;
	}

	public LocalDate getCheckOut() {
		return checkOut;
	}

	public String getHostEmail() {
		return hostEmail;
	}

	public String getGuestEmail() {
		return guestEmail;
	}

	public String getHostName() {
		return hostName;
	}

	public String getAddress() {
		return address;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}

	public void setGuestId(Long guestId) {
		this.guestId = guestId;
	}

	public void setHostId(Long hostId) {
		this.hostId = hostId;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setTotalNights(int totalNights) {
		this.totalNights = totalNights;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public void setCheckIn(LocalDate checkIn) {
		this.checkIn = checkIn;
	}

	public void setCheckOut(LocalDate checkOut) {
		this.checkOut = checkOut;
	}

	public void setHostEmail(String hostEmail) {
		this.hostEmail = hostEmail;
	}

	public void setGuestEmail(String guestEmail) {
		this.guestEmail = guestEmail;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
