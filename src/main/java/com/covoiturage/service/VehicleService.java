package com.covoiturage.service;

import com.covoiturage.dto.request.VehicleRequest;
import com.covoiturage.dto.response.VehicleResponse;

import java.util.List;

public interface VehicleService {
    VehicleResponse createVehicle(VehicleRequest request);

    VehicleResponse getVehicleById(Long id);

    List<VehicleResponse> getVehiclesByDriverId(Long driverId);

    VehicleResponse updateVehicle(Long id, VehicleRequest request);
}