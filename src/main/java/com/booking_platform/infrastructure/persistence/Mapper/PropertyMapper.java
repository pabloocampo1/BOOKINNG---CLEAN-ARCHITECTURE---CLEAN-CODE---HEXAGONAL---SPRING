package com.booking_platform.infrastructure.persistence.Mapper;

import com.booking_platform.domain.model.Property;
import com.booking_platform.infrastructure.persistence.entity.PropertyEntity;
import com.booking_platform.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "Spring")
public interface PropertyMapper {

    @Mapping(target = "userId", source = "user.userId" )
    Property toModel(PropertyEntity propertyEntity);

    @Mapping(target = "user", source = "userId", qualifiedByName = "mapUserIdToUserEntity")
    PropertyEntity toEntity(Property property);

    @Named("mapUserIdToUserEntity")
    default UserEntity mapUserIdToUserEntity(Long userId) {
        if (userId == null) return null;
        UserEntity user = new UserEntity();
        user.setUserId(userId);
        return user;
    }
}
