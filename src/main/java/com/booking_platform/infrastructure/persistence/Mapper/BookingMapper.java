package com.booking_platform.infrastructure.persistence.Mapper;

import com.booking_platform.domain.model.Booking;
import com.booking_platform.infrastructure.persistence.entity.BookingEntity;
import com.booking_platform.infrastructure.persistence.entity.PropertyEntity;
import com.booking_platform.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "Spring")
public interface BookingMapper {

	@Mapping(target = "propertyId", source = "property.propertyId")
	@Mapping(target = "guestId", source = "guest.userId")
	Booking toModel(BookingEntity bookingEntity);

	@Mapping(target = "property", source = "propertyId", qualifiedByName = "mapPropertyIdToPropertyEntity")
	@Mapping(target = "guest", source = "guestId", qualifiedByName = "mapGuestIdToUserEntity")
	BookingEntity toEntity(Booking booking);

	@Named("mapPropertyIdToPropertyEntity")
	default PropertyEntity mapPropertyIdToPropertyEntity(Long propertyId) {
		if (propertyId == null)
			return null;
		PropertyEntity property = new PropertyEntity();
		property.setPropertyId(propertyId);
		return property;
	}

	@Named("mapGuestIdToUserEntity")
	default UserEntity mapGuestIdToUserEntity(Long guestId) {
		if (guestId == null)
			return null;
		UserEntity guest = new UserEntity();
		guest.setUserId(guestId);
		return guest;
	}
}