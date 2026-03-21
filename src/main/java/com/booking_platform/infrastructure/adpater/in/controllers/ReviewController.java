package com.booking_platform.infrastructure.adpater.in.controllers;

import com.booking_platform.application.port.in.reviewUseCases.CreateReviewUseCase;
import com.booking_platform.application.port.in.reviewUseCases.GetReviewsByPropertyUseCase;
import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.domain.model.Review;
import com.booking_platform.infrastructure.rest.dto.ReviewDtoRequest;
import com.booking_platform.infrastructure.rest.dto.ReviewDtoResponse;
import com.booking_platform.infrastructure.rest.mapperRest.ReviewMapperRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

	private final CreateReviewUseCase createReviewUseCase;
	private final GetReviewsByPropertyUseCase getReviewsByPropertyUseCase;
	private final ReviewMapperRest reviewMapperRest;

	public ReviewController(CreateReviewUseCase createReviewUseCase,
			GetReviewsByPropertyUseCase getReviewsByPropertyUseCase,
			ReviewMapperRest reviewMapperRest) {
		this.createReviewUseCase = createReviewUseCase;
		this.getReviewsByPropertyUseCase = getReviewsByPropertyUseCase;
		this.reviewMapperRest = reviewMapperRest;

	}

	@PostMapping
	public ResponseEntity<ReviewDtoResponse> createReview(@Valid @RequestBody ReviewDtoRequest reviewDtoRequest,
			Authentication authentication) {

		String currentUser = authentication.getName();
		Review review = reviewMapperRest.toModel(reviewDtoRequest);
		Review createdReview = createReviewUseCase.execute(review, currentUser);

		return new ResponseEntity<>(this.reviewMapperRest.toResponse(createdReview), HttpStatus.CREATED);
	}

	@GetMapping("/property/{propertyId}")
	public ResponseEntity<List<ReviewDtoResponse>> getReviewsByProperty(@PathVariable Long propertyId) {
		List<ReviewDtoResponse> reviews = getReviewsByPropertyUseCase.execute(propertyId);
		return new ResponseEntity<>(reviews, HttpStatus.OK);
	}
}