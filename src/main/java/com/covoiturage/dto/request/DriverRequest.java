package com.covoiturage.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DriverRequest(
        @NotBlank(message = "le nom est oblig")
        String nom,

        @NotBlank(message = "le prenom est oblig")
        String prenom,

        @NotBlank(message = "l'email est oblig")
        @Email(message = "format d'email invalide")
        String email,

        @NotBlank(message = "le telephone est oblig")
        String telephone,

        @NotBlank(message = "le numero de permis est oblig")
        String licenseNumber
) {}