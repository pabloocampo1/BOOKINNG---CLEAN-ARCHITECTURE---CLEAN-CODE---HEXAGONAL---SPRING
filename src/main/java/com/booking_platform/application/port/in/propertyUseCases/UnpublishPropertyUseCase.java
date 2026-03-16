package com.booking_platform.application.port.in.propertyUseCases;

public interface UnpublishPropertyUseCase {

    void execute(Long propertyId, String currentUser);
}
