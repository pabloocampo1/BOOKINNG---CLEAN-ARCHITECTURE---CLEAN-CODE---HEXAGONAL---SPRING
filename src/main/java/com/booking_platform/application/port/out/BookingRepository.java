package com.booking_platform.application.port.out;

import java.util.List;
import java.util.Optional;

import com.booking_platform.domain.model.Booking;

public interface BookingRepository {

	Booking save(Booking booking);

	Optional<Booking> findById(Long id);

	List<Booking> findByGuestId(Long guestId);

	void deleteById(Long id);

	boolean existsCompletedBookingByGuestIdAndPropertyId(Long guestId, Long propertyId);

}
