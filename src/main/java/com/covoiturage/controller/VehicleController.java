package com.covoiturage.controller;

import com.covoiturage.dto.request.VehicleRequest;
import com.covoiturage.dto.response.VehicleResponse;
import com.covoiturage.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleResponse createVehicle(@Valid @RequestBody VehicleRequest request) {
        return vehicleService.createVehicle(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VehicleResponse getVehicleById(@PathVariable Long id) {
        return vehicleService.getVehicleById(id);
    }

    @GetMapping("/conducteurs/{driverId}")
    @ResponseStatus(HttpStatus.OK)
    public List<VehicleResponse> getVehiclesByDriverId(@PathVariable Long driverId) {
        return vehicleService.getVehiclesByDriverId(driverId);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VehicleResponse updateVehicle(@PathVariable Long id, @Valid @RequestBody VehicleRequest request) {
        return vehicleService.updateVehicle(id, request);
    }
}