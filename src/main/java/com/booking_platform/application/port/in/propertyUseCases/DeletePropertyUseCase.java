package com.booking_platform.application.port.in.propertyUseCases;

public interface DeletePropertyUseCase {
    void execute(Long propertyId, String currentUser);
}
