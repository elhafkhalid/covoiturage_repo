package com.covoiturage.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehicleRequest(
        @NotBlank(message = "la marque est oblig")
        String brand,

        @NotBlank(message = "Le modèle est oblig")
        String model,

        @NotBlank(message = "l'immatriculation est oblig")
        String licensePlate,

        @NotNull(message = "la capacite est oblig")
//        @Min(value = 1, message = "la capacite mini est de 1 ")
//        @Max(value = 8, message = "la capacite max est de 8 ")
        Integer capacity,

        @NotNull(message = "le conducteur est oblig")
        Long driverId
) {
}