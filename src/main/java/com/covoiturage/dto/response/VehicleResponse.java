package com.covoiturage.dto.response;

public record VehicleResponse(
        Long id,
        String brand,
        String model,
        String licensePlate,
        Integer capacity,
        Long driverId
) {
}