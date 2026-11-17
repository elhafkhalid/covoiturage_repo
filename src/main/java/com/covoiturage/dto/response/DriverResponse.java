package com.covoiturage.dto.response;

public record DriverResponse(
        Long id,
        String nom,
        String prenom,
        String email,
        String telephone,
        String licenseNumber
) {}