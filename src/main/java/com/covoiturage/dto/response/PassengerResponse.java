package com.covoiturage.dto.response;

public record PassengerResponse(
        Long id,
        String nom,
        String prenom,
        String email,
        String telephone
) {
}