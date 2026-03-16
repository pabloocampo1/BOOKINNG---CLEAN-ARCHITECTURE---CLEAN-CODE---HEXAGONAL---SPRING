package com.booking_platform.application.port.in.propertyUseCases;

public interface PublishPropertyUseCase {

    void execute(Long propertyId, String currentUser);
}
