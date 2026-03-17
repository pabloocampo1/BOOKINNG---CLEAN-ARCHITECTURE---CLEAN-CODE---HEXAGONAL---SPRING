package com.booking_platform.infrastructure.rest.mapperRest;

import com.booking_platform.domain.model.Availability;
import com.booking_platform.infrastructure.rest.dto.AvailabilityBlockDtoRequest;
import com.booking_platform.infrastructure.rest.dto.AvailabilityDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AvailabilityMapperRest {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "propertyId", source = "propertyId")
    @Mapping(target = "availabilityType", constant = "BLOCKED")
    Availability toBlockedModel(Long propertyId, AvailabilityBlockDtoRequest dto);

    @Mapping(target = "availabilityType", expression = "java(availability.getAvailabilityType().name())")
    AvailabilityDtoResponse toResponse(Availability availability);
}

