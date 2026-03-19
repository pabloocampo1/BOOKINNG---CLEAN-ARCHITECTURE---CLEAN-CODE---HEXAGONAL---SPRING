package com.booking_platform.domain.model;

public enum BookingStatus {
    /** El cliente solicitó la reserva pero el dueño no ha respondido o el pago no se ha procesado. */
    PENDING,

    /** La reserva ha sido aceptada y las fechas están bloqueadas en el calendario. */
    CONFIRMED,

    /** El cliente o el dueño cancelaron la reserva antes de que iniciara. */
    CANCELLED,

    /** El huésped ya llegó a la propiedad (Check-in realizado). */
    IN_PROGRESS,

    /** El huésped ya se fue y la estancia terminó (Check-out realizado). */
    COMPLETED,

    /** Hubo un problema (ej. el pago fue rechazado o el huésped nunca llegó - No Show). */
    FAILED
    
}
