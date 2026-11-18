package com.covoiturage.controller;

import com.covoiturage.dto.request.PassengerRequest;
import com.covoiturage.dto.response.PassengerResponse;
import com.covoiturage.service.PassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passagers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PassengerResponse createPassenger(@Valid @RequestBody PassengerRequest request) {
        return passengerService.createPassenger(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PassengerResponse updatePassenger(
            @PathVariable Long id,
            @Valid @RequestBody PassengerRequest req) {
        return passengerService.updatePassenger(id, req);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PassengerResponse getPassengerById(@PathVariable Long id) {
        return passengerService.getPassengerById(id);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PassengerResponse> getAllPassengers() {
        return passengerService.getAllPassengers();
    }


    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public PassengerResponse getPassengerByEmail(@RequestParam String email) {
        return passengerService.getPassengerByEmail(email);
    }
}