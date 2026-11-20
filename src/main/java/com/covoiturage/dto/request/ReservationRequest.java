package com.covoiturage.dto.request;

import jakarta.validation.constraints.NotNull;

public record ReservationRequest(
        @NotNull(message = "le passager est oblig")
        Long passengerId,

        @NotNull(message = "le trajet est oblig")
        Long tripId,

        @NotNull(message = "le point de monte est oblig")
        Long pickupStopId,

        @NotNull(message = "le point de descente est oblig")
        Long dropoffStopId,

        @NotNull(message = "le nombre de places est oblig")
        Integer numberOfSeats
) {
}