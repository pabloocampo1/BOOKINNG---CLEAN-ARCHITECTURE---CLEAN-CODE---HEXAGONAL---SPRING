package com.booking_platform.infrastructure.events.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.booking_platform.domain.Event.CreateBookingEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CreateBookingListener {

	@EventListener
	public void handleBookingCreatedEvent(CreateBookingEvent event) {
		log.info("=== [EVENT RECEIVED] type: {} ===", event.typeEvent());

		log.info("New Booking Created: ID #{} | Property: {} (#{})",
				event.getBookingId(),
				event.getPropertyType(),
				event.getPropertyId());

		log.info("Details: Guest {} -> Host {} | Stay: {} nights [{} to {}]",
				event.getGuestEmail(),
				event.getHostEmail(),
				event.getTotalNights(),
				event.getCheckIn(),
				event.getCheckOut());

		log.info("Financial: Total Price {} | Address: {}",
				event.getTotalPrice(),
				event.getAddress());

		log.info("=== [END EVENT LOG] ===");
	}

}
