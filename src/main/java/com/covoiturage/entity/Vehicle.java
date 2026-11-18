package com.covoiturage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "la marq est oblig")
    @Column(nullable = false)
    private String brand;

    @NotBlank(message = "le modele est oblig")
    @Column(nullable = false)
    private String model;

    @NotBlank(message = "matricul est oblig")
    @Column(nullable = false)
    private String licensePlate;


    @NotNull(message = "la capacite est oblig")
    @Min(value = 1, message = "la capacite mini est de 1 ")
    @Max(value = 8, message = "la capacite max est de 8 ")
    @Column(nullable = false)
    private Integer capacity;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;
}
