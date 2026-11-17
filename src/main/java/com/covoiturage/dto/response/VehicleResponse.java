package com.covoiturage.dto.response;

public record VehicleResponse(
        Long id,
        String driverNom,
        String driverPrenom,
        String brand,
        String model,
        String licensePlate,
        Integer capacity,
        Long driverId
) {
}