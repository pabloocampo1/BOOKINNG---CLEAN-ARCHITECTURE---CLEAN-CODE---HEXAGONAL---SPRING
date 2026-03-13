package com.booking_platform.infrastructure.rest.mapperRest;

import com.booking_platform.domain.model.Property;
import com.booking_platform.infrastructure.rest.dto.PropertyDtoRequest;
import com.booking_platform.infrastructure.rest.dto.PropertyDtoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PropertyMapperRest
{
    Property toModel(PropertyDtoRequest propertyDtoRequest);

    PropertyDtoResponse toResponse (Property property);
}
