package com.covoiturage.controller;

import com.covoiturage.dto.request.TripRequest;
import com.covoiturage.dto.response.TripResponse;
import com.covoiturage.service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<TripResponse> searchTrips(
            @RequestParam String depart,
            @RequestParam String arrivee,
            @RequestParam LocalDate date,
            @RequestParam BigDecimal prix,
            @RequestParam Integer places
    ) {
        return tripService.searchTrips(depart, arrivee, date, prix, places);
    }
}