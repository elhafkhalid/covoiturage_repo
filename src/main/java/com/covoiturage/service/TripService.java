package com.covoiturage.service;

import com.covoiturage.dto.request.TripRequest;
import com.covoiturage.dto.response.TripResponse;
import com.covoiturage.enums.StatutTrajet;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TripService {
    TripResponse createTrip(TripRequest request);

    List<TripResponse> searchTrips(String departureCity, String arrivalCity, LocalDate date, BigDecimal price,Integer places);
}