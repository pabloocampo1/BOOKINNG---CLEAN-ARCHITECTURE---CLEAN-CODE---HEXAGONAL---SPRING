package com.booking_platform.infrastructure.rest.mapperRest;

import com.booking_platform.domain.model.User;
import com.booking_platform.infrastructure.rest.dto.UserRegisterDto;
import com.booking_platform.infrastructure.rest.dto.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface UserMapperRest {

    User userRegisterDtoToModel(UserRegisterDto userRegisterDto);
     UserResponse toResponse(User user);
}
