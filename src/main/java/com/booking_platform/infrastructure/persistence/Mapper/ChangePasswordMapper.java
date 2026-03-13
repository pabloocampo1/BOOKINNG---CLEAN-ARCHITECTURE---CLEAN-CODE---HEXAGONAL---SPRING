package com.booking_platform.infrastructure.persistence.Mapper;

import com.booking_platform.domain.model.ChangeEmailCodeModel;
import com.booking_platform.infrastructure.persistence.entity.ChangeEmailCode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChangePasswordMapper {

    ChangeEmailCode toEntity(ChangeEmailCodeModel changeEmailCodeModel);

    ChangeEmailCodeModel toModel(ChangeEmailCode changeEmailCode);
}
