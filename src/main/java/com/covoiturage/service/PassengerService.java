package com.covoiturage.service;

import com.covoiturage.dto.request.PassengerRequest;
import com.covoiturage.dto.response.PassengerResponse;

import java.util.List;

public interface PassengerService {

    PassengerResponse createPassenger(PassengerRequest req);

    PassengerResponse updatePassenger(Long id, PassengerRequest req);

    PassengerResponse getPassengerById(Long id);

    PassengerResponse getPassengerByEmail(String email);

    List<PassengerResponse> getAllPassengers();

}
