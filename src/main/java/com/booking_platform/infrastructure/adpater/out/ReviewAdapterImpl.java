package com.booking_platform.infrastructure.adpater.out;

import com.booking_platform.application.port.out.ReviewRepository;
import com.booking_platform.domain.model.Review;
import com.booking_platform.infrastructure.persistence.Mapper.ReviewMapper;
import com.booking_platform.infrastructure.persistence.repositoryJpa.ReviewJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewAdapterImpl implements ReviewRepository {

	private final ReviewJpaRepository reviewJpaRepository;
	private final ReviewMapper reviewMapper;

	public ReviewAdapterImpl(ReviewJpaRepository reviewJpaRepository, ReviewMapper reviewMapper) {
		this.reviewJpaRepository = reviewJpaRepository;
		this.reviewMapper = reviewMapper;
	}

	@Override
	public Review save(Review review) {
		var reviewEntity = reviewJpaRepository.save(reviewMapper.toEntity(review));
		return reviewMapper.toModel(reviewEntity);
	}

	@Override
	public List<Review> findByPropertyId(Long propertyId) {
		return reviewJpaRepository.findByProperty_PropertyId(propertyId)
				.stream()
				.map(reviewMapper::toModel)
				.toList();
	}

	@Override
	public boolean existsByPropertyIdAndGuestId(Long propertyId, Long guestId) {
		return reviewJpaRepository.existsByProperty_PropertyIdAndGuest_UserId(propertyId, guestId);
	}
}