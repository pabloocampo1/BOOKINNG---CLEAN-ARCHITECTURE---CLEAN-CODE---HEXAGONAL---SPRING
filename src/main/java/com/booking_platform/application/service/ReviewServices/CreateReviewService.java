package com.booking_platform.application.service.ReviewServices;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.booking_platform.application.port.in.reviewUseCases.CreateReviewUseCase;
import com.booking_platform.application.port.out.BookingRepository;
import com.booking_platform.application.port.out.PropertyRepository;
import com.booking_platform.application.port.out.ReviewRepository;
import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.domain.exceptions.review.DuplicateReviewException;
import com.booking_platform.domain.exceptions.review.UnauthorizedOwnerReviewException;
import com.booking_platform.domain.exceptions.review.UnauthorizedReviewException;
import com.booking_platform.domain.model.Review;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CreateReviewService implements CreateReviewUseCase {

	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
	private final BookingRepository bookingRepository;
	private final PropertyRepository propertyRepository;

	public CreateReviewService(ReviewRepository reviewRepository, UserRepository userRepository,
			BookingRepository bookingRepository, PropertyRepository propertyRepository) {
		this.reviewRepository = reviewRepository;
		this.userRepository = userRepository;
		this.bookingRepository = bookingRepository;
		this.propertyRepository = propertyRepository;
	}

	@Override
	@Transactional
	public Review execute(Review review, String currentUser) {

		var guest = userRepository.findByUsername(currentUser)
				.orElseThrow(() -> new EntityNotFoundException("User not found with username: " + currentUser));

		var property = this.propertyRepository.findById(review.getPropertyId())
				.orElseThrow(
						() -> new EntityNotFoundException("Property not found with id: " + review.getPropertyId()));

		// Verify the guest is not the owner of the property

		if (Objects.equals(property.getUserId(), guest.getUserId())) {
			throw new UnauthorizedOwnerReviewException();
		}

		// Verify the guest has completed a booking for this property
		if (!bookingRepository.existsCompletedBookingByGuestIdAndPropertyId(guest.getUserId(),
				review.getPropertyId())) {
			throw new UnauthorizedReviewException();
		}

		// Check if guest already reviewed this property
		if (reviewRepository.existsByPropertyIdAndGuestId(review.getPropertyId(), guest.getUserId())) {
			throw new DuplicateReviewException();
		}

		property.addStar(review.getRating());

		review.setGuestId(guest.getUserId());

		this.propertyRepository.save(property);
		return reviewRepository.save(review);
	}
}