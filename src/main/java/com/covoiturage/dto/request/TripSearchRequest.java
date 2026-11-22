package com.covoiturage.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TripSearchRequest(
        String departureCity,
        String arrivalCity,
        LocalDate date,
        BigDecimal maxPrice
) {
}