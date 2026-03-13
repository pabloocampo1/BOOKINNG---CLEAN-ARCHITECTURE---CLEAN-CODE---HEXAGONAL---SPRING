package com.booking_platform.infrastructure.events.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.booking_platform.application.port.out.ChangePasswordRepository;
import com.booking_platform.domain.Event.DeleteTokenChangeEmailEvent;

@Component
public class DeleteTokenChangePasswordListener {

	private final ChangePasswordRepository repository;

	public DeleteTokenChangePasswordListener(ChangePasswordRepository repository) {
		this.repository = repository;
	}


	@EventListener
	@Async
	public void deleteToken(DeleteTokenChangeEmailEvent event){
		this.repository.delete(event.getId());
	}
	
}
