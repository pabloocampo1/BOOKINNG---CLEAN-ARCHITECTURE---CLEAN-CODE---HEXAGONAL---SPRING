package com.booking_platform.application.port.out;

import com.booking_platform.domain.model.Review;

import java.util.List;

public interface ReviewRepository {
	Review save(Review review);

	List<Review> findByPropertyId(Long propertyId);

	boolean existsByPropertyIdAndGuestId(Long propertyId, Long guestId);
}