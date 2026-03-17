package com.booking_platform.infrastructure.persistence.Mapper;

import com.booking_platform.domain.model.Availability;
import com.booking_platform.infrastructure.persistence.entity.AvailabilityEntity;
import com.booking_platform.infrastructure.persistence.entity.PropertyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "Spring")
public interface AvailabilityMapper {

    @Mapping(target = "propertyId", source = "property.propertyId")
    Availability toModel(AvailabilityEntity availabilityEntity);

    @Mapping(target = "property", source = "propertyId", qualifiedByName = "mapPropertyIdToPropertyEntity")
    AvailabilityEntity toEntity(Availability availability);

    @Named("mapPropertyIdToPropertyEntity")
    default PropertyEntity mapPropertyIdToPropertyEntity(Long propertyId) {
        if (propertyId == null) return null;
        PropertyEntity property = new PropertyEntity();
        property.setPropertyId(propertyId);
        return property;
    }
}

