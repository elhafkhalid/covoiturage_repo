package com.covoiturage.controller;

import com.covoiturage.dto.request.DriverRequest;
import com.covoiturage.dto.response.DriverResponse;
import com.covoiturage.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conducteurs")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverResponse createDriver(@Valid @RequestBody DriverRequest request) {
        return driverService.createDriver(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DriverResponse getDriverById(@PathVariable Long id) {
        return driverService.getDriverById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DriverResponse> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DriverResponse updateDriver(@PathVariable Long id, @Valid @RequestBody DriverRequest request) {
        return driverService.updateDriver(id, request);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public DriverResponse getDriverByEmail(@RequestParam String email) {
        return driverService.getDriverByEmail(email);
    }
}