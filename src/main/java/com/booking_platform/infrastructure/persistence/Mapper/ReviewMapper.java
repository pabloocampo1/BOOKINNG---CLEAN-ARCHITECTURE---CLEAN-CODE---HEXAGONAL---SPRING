package com.booking_platform.infrastructure.persistence.Mapper;

import com.booking_platform.domain.model.Review;
import com.booking_platform.infrastructure.persistence.entity.ReviewEntity;
import com.booking_platform.infrastructure.persistence.entity.PropertyEntity;
import com.booking_platform.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "Spring")
public interface ReviewMapper {

	@Mapping(target = "propertyId", source = "property.propertyId")
	@Mapping(target = "guestId", source = "guest.userId")
	Review toModel(ReviewEntity reviewEntity);

	@Mapping(target = "property", source = "propertyId", qualifiedByName = "mapPropertyIdToPropertyEntity")
	@Mapping(target = "guest", source = "guestId", qualifiedByName = "mapGuestIdToUserEntity")
	ReviewEntity toEntity(Review review);

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
		UserEntity user = new UserEntity();
		user.setUserId(guestId);
		return user;
	}
}