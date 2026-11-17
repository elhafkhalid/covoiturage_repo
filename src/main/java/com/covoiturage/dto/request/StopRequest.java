package com.covoiturage.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record StopRequest(
        @NotBlank(message = "la ville est oblig")
        String city,
        @NotNull(message = "l'ordre est oblig")
        Integer stopOrder,
        @NotNull(message = "l'heure de passage est oblig")
        LocalDateTime arrivalTime,
        @NotNull(message = "le prix est oblig")
        BigDecimal priceFromStart
) {
}