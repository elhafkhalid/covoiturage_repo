package com.covoiturage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "stops")
public class Stop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La ville est obligatoire")
    @Column(nullable = false)
    private String city;

    @NotNull(message = "l'ordre est oblig")
    @Column(name = "stop_order", nullable = false)
    private Integer stopOrder;

    @NotNull(message = "l'heure de passage est oblig")
    @Column(nullable = false)
    private LocalDateTime arrivalTime;

    @NotNull(message = "Le prix depuis le depart est oblig")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal priceFromStart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;
}