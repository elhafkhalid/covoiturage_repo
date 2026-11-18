package com.covoiturage.service;

import com.covoiturage.dto.request.DriverRequest;
import com.covoiturage.dto.response.DriverResponse;

import java.util.List;

public interface DriverService {
    DriverResponse createDriver(DriverRequest request);

    DriverResponse getDriverById(Long id);

    List<DriverResponse> getAllDrivers();

    DriverResponse updateDriver(Long id, DriverRequest request);

    DriverResponse getDriverByEmail(String email);
}