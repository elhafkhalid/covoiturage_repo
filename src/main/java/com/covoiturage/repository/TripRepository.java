package com.covoiturage.repository;

import com.covoiturage.entity.Trip;
import com.covoiturage.enums.StatutTrajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    @Query("""
                SELECT DISTINCT t FROM Trip t
                JOIN t.stops s1
                JOIN t.stops s2
                WHERE t.status = :status
                  AND s1.city = :departureCity
                  AND s2.city = :arrivalCity
                  AND s1.stopOrder < s2.stopOrder
            """)
    List<Trip> searchTrips(
            @Param("departureCity") String departureCity,
            @Param("arrivalCity") String arrivalCity,
            @Param("status") StatutTrajet status
    );



    @Query("""
                SELECT DISTINCT t FROM Trip t
                JOIN t.stops s1
                JOIN t.stops s2
                WHERE t.status = :status
                  AND s1.city = :departureCity
                  AND s2.city = :arrivalCity
                  AND s1.stopOrder < s2.stopOrder
                  AND CAST(s1.arrivalTime AS date) = :date
                  AND (s2.priceFromStart - s1.priceFromStart) <= :maxPrice
            """)
    List<Trip> searchTripsWithFilters(
            @Param("departureCity") String departureCity,
            @Param("arrivalCity") String arrivalCity,
            @Param("date") LocalDate date,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("status") StatutTrajet status
    );


}