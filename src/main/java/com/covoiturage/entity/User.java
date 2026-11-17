package com.covoiturage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass

public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "le nom est oblig")
    @Column(nullable = false, length = 50)
    private String nom;

    @NotBlank(message = "le prenom est oblig")
    @Column(nullable = false, length = 50)
    private String prenom;

    @NotBlank(message = "email est oblig")
    @Email(message = "format email invalid")
    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @NotBlank(message = "le telef est oblig")
    @Pattern(regexp = "^[0-9+ ]{8,20}$", message = "Format de téléphone invalide")
    @Column(nullable = false, length = 20)
    private String telephone;

    @Column(nullable = false)
    private Boolean isActive = true;
}
