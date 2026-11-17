package com.covoiturage.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record TripRequest(
        @NotNull(message = "la date de départ est oblig")
        LocalDateTime departureDate,
        @NotBlank(message = "la ville de depart est oblig")
        String departureCity,
        @NotBlank(message = "la ville d'arrive est oblig")
        String arrivalCity,
        @NotNull(message = "le nombre de places est oblig")
        Integer proposedSeats,
        @NotNull(message = "le vehicule est oblig")
        Long vehicleId,
        @NotNull(message = "les arrets sont oblig")
        List<StopRequest> stops
) {
}