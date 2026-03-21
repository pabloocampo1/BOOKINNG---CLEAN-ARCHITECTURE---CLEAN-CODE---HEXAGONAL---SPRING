package com.booking_platform.application.service.ReviewServices;

import org.springframework.stereotype.Service;

import com.booking_platform.application.port.in.reviewUseCases.GetReviewsByPropertyUseCase;
import com.booking_platform.application.port.out.ReviewRepository;
import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.domain.model.Review;
import com.booking_platform.infrastructure.rest.dto.ReviewDtoResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetReviewsByPropertyService implements GetReviewsByPropertyUseCase {

	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;

	public GetReviewsByPropertyService(ReviewRepository reviewRepository, UserRepository userRepository) {
		this.reviewRepository = reviewRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<ReviewDtoResponse> execute(Long propertyId) {
		List<Review> reviews = reviewRepository.findByPropertyId(propertyId);

		return reviews.stream()
				.map(review -> {
					String guestName = userRepository.findById(review.getGuestId())
							.map(user -> user.getFullName())
							.orElse("Usuario desconocido");

					return new ReviewDtoResponse(
							review.getId(),
							review.getPropertyId(),
							review.getGuestId(),
							guestName,
							review.getRating(),
							review.getComment(),
							review.getCreatedAt());
				})
				.collect(Collectors.toList());
	}
}