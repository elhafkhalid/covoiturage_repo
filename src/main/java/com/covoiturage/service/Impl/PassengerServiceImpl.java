package com.covoiturage.service.Impl;

import com.covoiturage.dto.request.PassengerRequest;
import com.covoiturage.dto.response.PassengerResponse;
import com.covoiturage.entity.Passenger;
import com.covoiturage.exception.BusinessException;
import com.covoiturage.exception.ResourceNotFoundException;
import com.covoiturage.mapper.PassengerMapper;
import com.covoiturage.repository.PassengerRepository;
import com.covoiturage.service.PassengerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Spring au lieu de Jakarta

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    @Override
    @Transactional
    public PassengerResponse createPassenger(PassengerRequest req) {

        log.info("creation d'1 nv passager avec email : {} ", req.email());

        if (passengerRepository.existsByEmail(req.email())) {
            throw new BusinessException("cet email est deja utilise par un autre passager");
        }

        Passenger passenger = passengerMapper.toEntity(req);
        return passengerMapper.toResponse(passengerRepository.save(passenger));
    }

    public PassengerResponse updatePassenger(Long id, PassengerRequest req) {

        log.info("mise a jour du passager avec id : {} ", id);
        Passenger existingPassenger = passengerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("passager intrv avec id : " + id)
                );

        if (passengerRepository.existsByEmail(req.email()) && !existingPassenger.getEmail().equals(req.email())) {
            throw new BusinessException("cet email est deja utilise par un autre passager");
        }

        passengerMapper.updateEntityFromRequest(req, existingPassenger);

        Passenger updatedPass = passengerRepository.save(existingPassenger);

        return passengerMapper.toResponse(updatedPass);

    }

    @Override
    @Transactional(readOnly = true)
    public PassengerResponse getPassengerById(Long id) {
        log.info("Recherche du passager avec id : {} ", id);

        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passager introuvable avec l'id : " + id));

        return passengerMapper.toResponse(passenger);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PassengerResponse> getAllPassengers() {

        log.info("Recuperation de tous les passagers");
        List<Passenger> passengers = passengerRepository.findAll();
        return passengerMapper.toResponseList(passengers);
    }

    @Override
    @Transactional(readOnly = true)
    public PassengerResponse getPassengerByEmail(String email) {
        log.info("Recherche d'un passager par email : {} ", email);

        Passenger passenger = passengerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Passager introuvable avec cet email : " + email));

        return passengerMapper.toResponse(passenger);
    }
}