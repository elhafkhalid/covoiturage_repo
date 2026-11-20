package com.covoiturage.dto.response;

import com.covoiturage.enums.StatutReservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservationResponse(
        Long id,
        Integer numberOfSeats,
        LocalDateTime reservationDate,
        StatutReservation status,
        BigDecimal totalPrice,
        Long passengerId,
        Long tripId,
        String pickupCity,
        String dropoffCity
) {
}