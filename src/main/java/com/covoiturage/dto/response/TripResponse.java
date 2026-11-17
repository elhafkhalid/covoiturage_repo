package com.covoiturage.dto.response;

import com.covoiturage.enums.StatutTrajet;

import java.time.LocalDateTime;
import java.util.List;

public record TripResponse(
        Long id,
        LocalDateTime departureDate,
        String departureCity,
        String arrivalCity,
        Integer proposedSeats,
        Integer availableSeats,
        StatutTrajet status,
        Long vehicleId,
        String vehicleBrand,
        List<StopResponse> stops
) {
}