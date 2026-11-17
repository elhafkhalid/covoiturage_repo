package com.covoiturage.repository;

import com.covoiturage.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    // On ajoutera les requêtes complexes plus tard (API 4)
}