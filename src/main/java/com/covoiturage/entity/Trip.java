package com.covoiturage.entity;

import com.covoiturage.enums.StatutReservation;
import com.covoiturage.enums.StatutTrajet;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La date de depart est oblig")
    @Column(nullable = false)
    private LocalDateTime departureDate;

    @Column(nullable = false)
    private String departureCity;

    @Column(nullable = false)
    private String arrivalCity;

    @NotNull(message = "Le nombre de places proposes est oblig")
    @Column(nullable = false)
    private Integer proposedSeats;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutTrajet status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("stopOrder ASC")
    private List<Stop> stops = new ArrayList<>();

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    public int getAvailableSeats() {
        if (reservations == null) return proposedSeats;

        int reserved = reservations.stream()
                .filter(r -> r.getStatus() == StatutReservation.CONFIRMEE)
                .mapToInt(Reservation::getNumberOfSeats)
                .sum();
        return proposedSeats - reserved;
    }
}