package com.booking_platform.infrastructure.rest.mapperRest;

import com.booking_platform.domain.model.Review;
import com.booking_platform.infrastructure.rest.dto.ReviewDtoRequest;
import com.booking_platform.infrastructure.rest.dto.ReviewDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapperRest {

	@Mapping(target = "guestId", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	Review toModel(ReviewDtoRequest reviewDtoRequest);

	@Mapping(target = "guestName", ignore = true)
	ReviewDtoResponse toResponse(Review review);
}