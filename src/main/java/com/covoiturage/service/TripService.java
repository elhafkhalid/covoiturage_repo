package com.covoiturage.service;

import com.covoiturage.dto.request.TripRequest;
import com.covoiturage.dto.response.TripResponse;

import java.util.List;

public interface TripService {
    TripResponse createTrip(TripRequest request);
}