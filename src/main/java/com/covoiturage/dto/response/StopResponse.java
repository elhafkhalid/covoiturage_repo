package com.covoiturage.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record StopResponse(
        Long id,
        String city,
        Integer stopOrder,
        LocalDateTime arrivalTime,
        BigDecimal priceFromStart
) {
}