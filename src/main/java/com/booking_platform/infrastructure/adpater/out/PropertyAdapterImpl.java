package com.booking_platform.infrastructure.adpater.out;

import com.booking_platform.application.port.out.PropertyRepository;
import com.booking_platform.domain.model.Property;
import com.booking_platform.infrastructure.persistence.Mapper.PropertyMapper;
import com.booking_platform.infrastructure.persistence.entity.PropertyEntity;
import com.booking_platform.infrastructure.persistence.repositoryJpa.PropertyJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public Page<Property> findAllByCity( int page, int size ,String city) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rating").descending());

        return this.propertyJpaRepository
                .findAllByLocationCityIgnoreCaseAndIsPublishedTrue(pageable, city)
                .map(this.propertyMapper::toModel);
    }

    @Override
    public Page<Property> findAllByCountry(int page, int size, String country) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rating").descending());

        return this.propertyJpaRepository
                .findAllByCountry(pageable, country)
                .map(this.propertyMapper::toModel);
    }

    @Override
    public Page<Property> findAllPage(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("rating").descending());

        return this.propertyJpaRepository
                .findAll(pageable)
                .map(this.propertyMapper::toModel);
    }

    @Override
    public void deleteById(Long id) {
       this.propertyJpaRepository.deleteById(id);
    }

    @Override
    public Page<Property> findAllByUserUserId( int page, int size,Long userId) {
        Pageable pageable = PageRequest.of(page, size);
        return this.propertyJpaRepository.findAllByUserUserId(pageable, userId).map(this.propertyMapper::toModel);
    }
}
