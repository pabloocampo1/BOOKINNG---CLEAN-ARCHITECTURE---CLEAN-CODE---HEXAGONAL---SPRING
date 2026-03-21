package com.booking_platform.application.port.in.reviewUseCases;

import com.booking_platform.domain.model.Review;

public interface CreateReviewUseCase {
	Review execute(Review review, String currentUser);
}