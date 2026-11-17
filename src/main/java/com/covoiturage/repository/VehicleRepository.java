package com.covoiturage.repository;

import com.covoiturage.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    // Pour lister les véhicules d'un conducteur (API 2)
    List<Vehicle> findByDriverId(Long driverId);
    boolean existsByLicensePlate(String licensePlate);
}