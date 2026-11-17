package com.covoiturage.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PassengerRequest(
        @NotBlank(message = "le nom est oblig")
        String nom,

        @NotBlank(message = "le prenom est oblig")
        String prenom,

        @NotBlank(message = "l'email est oblig") @Email(message = "format d'email invalid")
        String email,

        @NotBlank(message = "le téléphone est oblig")
        String telephone
) {
}