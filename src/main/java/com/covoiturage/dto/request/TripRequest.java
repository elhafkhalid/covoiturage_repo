package com.covoiturage.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public record TripRequest(
        @NotNull(message = "la date de dpart est oblig")
        @Future(message = "la date de depart doit etre dans le futur")
        LocalDateTime departureDate,

        @NotBlank(message = "la ville de depart est oblig")
        String departureCity,

        @NotBlank(message = "la ville d'arrive est oblig")
        String arrivalCity,

        @NotNull(message = "le nombre de places est oblig")
        @Min(value = 1, message = "il faut proposer au moins 1 place")
        Integer proposedSeats,

        @NotNull(message = "l'id de vehicule est oblig")
        Long vehicleId,

        @NotNull(message = "les arrets sont oblig")
        @Size(min = 2,message =  "un trajet doit avoir au moins 2 arrets (depart et arrive)")
        @Valid
        List<StopRequest> stops
) {
}