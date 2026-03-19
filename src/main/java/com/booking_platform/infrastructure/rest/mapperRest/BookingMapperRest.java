package com.booking_platform.infrastructure.rest.mapperRest;

import com.booking_platform.domain.model.Booking;
import com.booking_platform.infrastructure.rest.dto.BookingDtoRequest;
import com.booking_platform.infrastructure.rest.dto.BookingDtoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapperRest {

	Booking toModel(BookingDtoRequest bookingDtoRequest);

	BookingDtoResponse toResponse(Booking booking);
}