package com.booking_platform.infrastructure.adpater.in.controllers;

import com.booking_platform.application.port.in.propertyUseCases.*;
import com.booking_platform.domain.model.Property;
import com.booking_platform.infrastructure.rest.dto.AvailabilityBlockDtoRequest;
import com.booking_platform.infrastructure.rest.dto.AvailabilityDtoResponse;
import com.booking_platform.infrastructure.rest.dto.PropertyDtoRequest;
import com.booking_platform.infrastructure.rest.dto.PropertyDtoResponse;
import com.booking_platform.infrastructure.rest.mapperRest.AvailabilityMapperRest;
import com.booking_platform.infrastructure.rest.mapperRest.PropertyMapperRest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    private final CreatePropertyUseCase createPropertyUseCase;
    private final GetAllPropertiesByCityUseCase getAllPropertiesByCityUseCase;
    private final GetAllPropertiesByCountryUseCase getAllPropertiesByCountryUseCase;
    private final DeletePropertyUseCase deletePropertyUseCase;
    private final PropertyMapperRest propertyMapperRest;
    private final AvailabilityMapperRest availabilityMapperRest;
    private final PublishPropertyUseCase publishPropertyUseCase;
    private final UnpublishPropertyUseCase unpublishPropertyUseCase;
    private final GetMyPropertiesUseCase getMyPropertiesUseCase;
    private final CreateAvailabilityBlockUseCase createAvailabilityBlockUseCase;


    public PropertyController(CreatePropertyUseCase createPropertyUseCase, GetAllPropertiesByCityUseCase getAllPropertiesByCityUseCase, GetAllPropertiesByCountryUseCase getAllPropertiesByCountryUseCase, DeletePropertyUseCase deletePropertyUseCase, PropertyMapperRest propertyMapperRest, AvailabilityMapperRest availabilityMapperRest, PublishPropertyUseCase publishPropertyUseCase, UnpublishPropertyUseCase unpublishPropertyUseCase, GetMyPropertiesUseCase getMyPropertiesUseCase, CreateAvailabilityBlockUseCase createAvailabilityBlockUseCase) {
        this.createPropertyUseCase = createPropertyUseCase;
        this.getAllPropertiesByCityUseCase = getAllPropertiesByCityUseCase;
        this.getAllPropertiesByCountryUseCase = getAllPropertiesByCountryUseCase;
        this.deletePropertyUseCase = deletePropertyUseCase;
        this.propertyMapperRest = propertyMapperRest;
        this.availabilityMapperRest = availabilityMapperRest;
        this.publishPropertyUseCase = publishPropertyUseCase;
        this.unpublishPropertyUseCase = unpublishPropertyUseCase;
        this.getMyPropertiesUseCase = getMyPropertiesUseCase;
        this.createAvailabilityBlockUseCase = createAvailabilityBlockUseCase;
    }


    @PostMapping
    public ResponseEntity<PropertyDtoResponse> save(@RequestBody PropertyDtoRequest propertyDtoRequest, Authentication authentication) {
        String ouwner = authentication.getName();
        Property propertyModel = this.propertyMapperRest.toModel(propertyDtoRequest);
        Property propertySaved = this.createPropertyUseCase.execute(ouwner, propertyModel);
        PropertyDtoResponse property = this.propertyMapperRest.toResponse(propertySaved);

        return new ResponseEntity<>(property, HttpStatus.CREATED);
    }


    @GetMapping("/city")
    public ResponseEntity<Page<PropertyDtoResponse>> getAllByCity(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            @RequestParam(name = "city", required = true) String city

    ) {
        return new ResponseEntity<>(this.getAllPropertiesByCityUseCase.execute(page, size, city).map(this.propertyMapperRest::toResponse), HttpStatus.OK);
    }

    @GetMapping("/country")
    public ResponseEntity<Page<PropertyDtoResponse>> getAllByCountry(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            @RequestParam(name = "country", required = true) String country

    ) {
        return new ResponseEntity<>(this.getAllPropertiesByCountryUseCase.execute(page, size, country).map(this.propertyMapperRest::toResponse), HttpStatus.OK);
    }


    @DeleteMapping("/{propertyId}")
    public ResponseEntity<Void> delete(@PathVariable("propertyId") Long propertyId, Authentication authentication) {
        String currentUser = authentication.getName();
        this.deletePropertyUseCase.execute(propertyId, currentUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/publish/{propertyId}")
    public ResponseEntity<Void> publishProperty(@PathVariable("propertyId") Long propertyId, Authentication authentication) {
        this.publishPropertyUseCase.execute(propertyId, authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/unpublish/{propertyId}")
    public ResponseEntity<Void> unpublishProperty(@PathVariable("propertyId") Long propertyId, Authentication authentication) {
        this.unpublishPropertyUseCase.execute(propertyId, authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/my-properties")
    public ResponseEntity<Page<PropertyDtoResponse>> getMyProperties(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            Authentication authentication) {

        String currentUser = authentication.getName();
        return new ResponseEntity<>(this.getMyPropertiesUseCase.execute(page, size ,currentUser).map(this.propertyMapperRest::toResponse), HttpStatus.OK);
    }

    @PostMapping("/{propertyId}/availability/block")
    public ResponseEntity<AvailabilityDtoResponse> blockAvailability(
            @PathVariable("propertyId") Long propertyId,
            @RequestBody AvailabilityBlockDtoRequest dto,
            Authentication authentication
    ) {
        String currentUser = authentication.getName();
        var availability = this.availabilityMapperRest.toBlockedModel(propertyId, dto);
        var saved = this.createAvailabilityBlockUseCase.execute(currentUser, availability);
        return new ResponseEntity<>(this.availabilityMapperRest.toResponse(saved), HttpStatus.CREATED);
    }


}
