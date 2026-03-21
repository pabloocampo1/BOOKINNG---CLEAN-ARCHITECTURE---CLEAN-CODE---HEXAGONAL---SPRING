package com.booking_platform.application.port.in.reviewUseCases;

import com.booking_platform.infrastructure.rest.dto.ReviewDtoResponse;

import java.util.List;

public interface GetReviewsByPropertyUseCase {
	List<ReviewDtoResponse> execute(Long propertyId);
}