package com.covoiturage.service;

import com.covoiturage.dto.request.ReservationRequest;
import com.covoiturage.dto.response.ReservationResponse;

import java.util.List;

public interface ReservationService {
    ReservationResponse createReservation(ReservationRequest request);
    ReservationResponse getReservationById(Long id);
    List<ReservationResponse> getPassengerReservations(Long passengerId);
    void cancelReservation(Long id);
}