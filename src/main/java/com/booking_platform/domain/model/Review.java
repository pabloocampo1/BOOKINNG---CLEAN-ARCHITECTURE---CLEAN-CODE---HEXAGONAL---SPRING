package com.booking_platform.domain.model;

import java.time.LocalDateTime;

public class Review {
	private Long id;
	private Long propertyId;
	private Long guestId;
	private int rating;
	private String comment;
	private LocalDateTime createdAt;

	public Review(Long id, Long propertyId, Long guestId, int rating, String comment, LocalDateTime createdAt) {
		this.id = id;
		this.propertyId = propertyId;
		this.guestId = guestId;
		this.rating = rating;
		this.comment = comment;
		this.createdAt = createdAt;
		validateRating();
	}

	private void validateRating() {
		if (rating < 1 || rating > 5) {
			throw new IllegalArgumentException("Rating must be between 1 and 5");
		}
	}

	// Getters and setters
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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
		validateRating();
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}