package com.booking_platform.infrastructure.adpater.in.controllers;

import com.booking_platform.application.port.in.propertyUseCases.CreatePropertyUseCase;
import com.booking_platform.domain.model.Property;
import com.booking_platform.infrastructure.rest.dto.PropertyDtoRequest;
import com.booking_platform.infrastructure.rest.dto.PropertyDtoResponse;
import com.booking_platform.infrastructure.rest.mapperRest.PropertyMapperRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    private final CreatePropertyUseCase createPropertyUseCase;
    private final PropertyMapperRest propertyMapperRest;

    public PropertyController(CreatePropertyUseCase createPropertyUseCase, PropertyMapperRest propertyMapperRest) {
        this.createPropertyUseCase = createPropertyUseCase;
        this.propertyMapperRest = propertyMapperRest;
    }


    @PostMapping
    public ResponseEntity<PropertyDtoResponse> save(@RequestBody PropertyDtoRequest propertyDtoRequest, Authentication authentication){
        String ouwner = authentication.getName();
        Property propertyModel = this.propertyMapperRest.toModel(propertyDtoRequest);
        Property propertySaved =  this.createPropertyUseCase.execute(ouwner,propertyModel);
        PropertyDtoResponse property = this.propertyMapperRest.toResponse(propertySaved);

        return new ResponseEntity<>(property, HttpStatus.CREATED);
    }
}
