package com.booking_platform.infrastructure.persistence.Mapper;

import com.booking_platform.domain.model.User;
import com.booking_platform.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper( componentModel = "Spring")
public interface UserMapper {

    User toModel(UserEntity userEntity);
    UserEntity toEntity(User user);
}
