package com.booking_platform.infrastructure.adpater.out;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.booking_platform.application.port.out.BookingRepository;
import com.booking_platform.domain.model.Booking;
import com.booking_platform.infrastructure.persistence.Mapper.BookingMapper;
import com.booking_platform.infrastructure.persistence.repositoryJpa.BookingJpaRepository;

@Repository
public class BookingAdpaterImp implements BookingRepository {

	private final BookingJpaRepository bookingJpaRepository;
	private final BookingMapper bookingMapper;

	public BookingAdpaterImp(BookingJpaRepository bookingJpaRepository, BookingMapper bookingMapper) {
		this.bookingJpaRepository = bookingJpaRepository;
		this.bookingMapper = bookingMapper;
	}

	@Override
	public Booking save(Booking booking) {
		return this.bookingMapper.toModel(this.bookingJpaRepository.save(this.bookingMapper.toEntity(booking)));
	}

	@Override
	public Optional<Booking> findById(Long id) {

		return this.bookingJpaRepository.findById(id).map(bookingEntity -> this.bookingMapper.toModel(bookingEntity));
	}

	@Override
	public void deleteById(Long id) {
		this.bookingJpaRepository.deleteById(id);
	}

}
