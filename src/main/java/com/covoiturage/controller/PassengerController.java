package com.covoiturage.controller;

import com.covoiturage.dto.request.PassengerRequest;
import com.covoiturage.dto.response.PassengerResponse;
import com.covoiturage.service.PassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passagers")
@RequiredArgsConstructor
public class PassengerController {
    private final PassengerService passengerService;

    @PostMapping
    public ResponseEntity<PassengerResponse> createPassenger(@Valid @RequestBody PassengerRequest req) {
        PassengerResponse response = passengerService.createPassenger(req);
        return ResponseEntity.status((HttpStatus.CREATED)).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponse> getPassengerById(@PathVariable Long id) {
        PassengerResponse response = passengerService.getPassengerById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PassengerResponse>> getAllPassengers() {
        List<PassengerResponse> response = passengerService.getAllPassengers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<PassengerResponse> getPassengerByEmail(@RequestParam String email) {
        PassengerResponse response = passengerService.getPassengerByEmail(email);
        return ResponseEntity.ok(response);
    }

}
