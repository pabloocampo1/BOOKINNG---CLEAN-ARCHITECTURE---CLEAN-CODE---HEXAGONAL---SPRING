package com.booking_platform.infrastructure.adpater.out;

import com.booking_platform.application.port.out.AvailabilityRepository;
import com.booking_platform.domain.model.Availability;
import com.booking_platform.infrastructure.persistence.Mapper.AvailabilityMapper;
import com.booking_platform.infrastructure.persistence.entity.AvailabilityEntity;
import com.booking_platform.infrastructure.persistence.repositoryJpa.AvailabilityJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class AvailabilityAdapterImpl implements AvailabilityRepository {

    private final AvailabilityJpaRepository availabilityJpaRepository;
    private final AvailabilityMapper availabilityMapper;

    public AvailabilityAdapterImpl(AvailabilityJpaRepository availabilityJpaRepository, AvailabilityMapper availabilityMapper) {
        this.availabilityJpaRepository = availabilityJpaRepository;
        this.availabilityMapper = availabilityMapper;
    }

    @Override
    public Availability save(Availability availability) {
        AvailabilityEntity saved = this.availabilityJpaRepository.save(this.availabilityMapper.toEntity(availability));
        return this.availabilityMapper.toModel(saved);
    }

    @Override
    public Optional<Availability> findById(Long id) {
        return this.availabilityJpaRepository.findById(id).map(this.availabilityMapper::toModel);
    }

    @Override
    public List<Availability> findAllByPropertyId(Long propertyId) {
        return this.availabilityJpaRepository.findAllByPropertyPropertyId(propertyId)
                .stream()
                .map(this.availabilityMapper::toModel)
                .toList();
    }

    @Override
    public boolean existsOverlappingRange(Long propertyId, LocalDate startDate, LocalDate endDate) {
        return this.availabilityJpaRepository.existsOverlappingRange(propertyId, startDate, endDate);
    }

    @Override
    public void deleteById(Long id) {
        this.availabilityJpaRepository.deleteById(id);
    }
}

