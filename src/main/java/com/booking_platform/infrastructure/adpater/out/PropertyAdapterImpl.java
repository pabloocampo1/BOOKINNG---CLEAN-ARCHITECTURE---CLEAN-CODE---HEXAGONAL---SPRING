package com.booking_platform.infrastructure.adpater.out;

import com.booking_platform.application.port.out.PropertyRepository;
import com.booking_platform.domain.model.Property;
import com.booking_platform.infrastructure.persistence.Mapper.PropertyMapper;
import com.booking_platform.infrastructure.persistence.entity.PropertyEntity;
import com.booking_platform.infrastructure.persistence.repositoryJpa.PropertyJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PropertyAdapterImpl implements PropertyRepository {

    private final PropertyJpaRepository propertyJpaRepository;
    private final PropertyMapper propertyMapper;

    public PropertyAdapterImpl(PropertyJpaRepository propertyJpaRepository, PropertyMapper propertyMapper) {
        this.propertyJpaRepository = propertyJpaRepository;
        this.propertyMapper = propertyMapper;
    }


    @Override
    public Property save(Property property) {

        PropertyEntity propertyEntity = this.propertyJpaRepository.save(this.propertyMapper.toEntity(property));

        return this.propertyMapper.toModel(propertyEntity);
    }

    @Override
    public List<Property> findAll() {
        return this.propertyJpaRepository.findAll().stream().map(this.propertyMapper::toModel).toList();
    }

    @Override
    public Optional<Property> findById(Long propertyId) {
        return this.propertyJpaRepository.findById(propertyId).map(this.propertyMapper::toModel);
    }
}
