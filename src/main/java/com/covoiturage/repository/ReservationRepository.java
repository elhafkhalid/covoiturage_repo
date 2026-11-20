package com.covoiturage.repository;

import com.covoiturage.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByPassengerId(Long passengerId);

    // Pour lister les réservations d'un trajet (API 3)
    List<Reservation> findByTripId(Long tripId);

    // Pour calculer les places restantes (méthode de Trip.getAvailableSeats())
    // Mais on peut aussi le faire via une requête SQL optimisée :
    // (Optionnel, on verra plus tard)

    // Pour vérifier si un passager a déjà réservé sur ce trajet (règle métier ?)
    boolean existsByPassengerIdAndTripId(Long passengerId, Long tripId);
}