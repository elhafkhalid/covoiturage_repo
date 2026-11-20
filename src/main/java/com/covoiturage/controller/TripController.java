package com.covoiturage.controller;

import com.covoiturage.dto.request.TripRequest;
import com.covoiturage.dto.response.TripResponse;
import com.covoiturage.service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trajets")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TripResponse createTrip(@Valid @RequestBody TripRequest request) {
        return tripService.createTrip(request);
    }
}