package com.covoiturage.service.Impl;


import com.covoiturage.dto.request.StopRequest;
import com.covoiturage.dto.request.TripRequest;
import com.covoiturage.dto.response.TripResponse;
import com.covoiturage.entity.Stop;
import com.covoiturage.entity.Trip;
import com.covoiturage.entity.Vehicle;
import com.covoiturage.enums.StatutTrajet;
import com.covoiturage.exception.BusinessException;
import com.covoiturage.exception.ResourceNotFoundException;
import com.covoiturage.mapper.StopMapper;
import com.covoiturage.mapper.TripMapper;
import com.covoiturage.repository.TripRepository;
import com.covoiturage.repository.VehicleRepository;
import com.covoiturage.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class TripServiceImpl implements TripService {

    private final VehicleRepository vehicleRepository;
    private final TripMapper tripMapper;
    private final StopMapper stopMapper;
    private final TripRepository tripRepository;

    public TripResponse createTrip(TripRequest request) {

        log.info("Publication d'un trajet ");

        Vehicle vehicle = getVehicle(request.vehicleId());
        validateCapacity(request.proposedSeats(), vehicle.getCapacity());
        List<StopRequest> sortedStops = sortStopsByOrder(request.stops());
        validateStops(sortedStops, request);

        Trip savedTrip = buildAndSaveTrip(request, vehicle, sortedStops);

        return tripMapper.toResponse(savedTrip);
    }

    private Vehicle getVehicle(Long vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("vehicle intrv avec id : " + vehicleId));
    }

    private void validateCapacity(Integer proposedSeats, Integer capacity) {
        if (proposedSeats > capacity)
            throw new BusinessException("le nmbres propose ne doit pas deppaser capacity!!");
    }

    private List<StopRequest> sortStopsByOrder(List<StopRequest> stops) {
        return stops.stream()
                .sorted((s1, s2) -> s1.stopOrder().compareTo(s2.stopOrder()))
                .toList();
    }

    private void validateStops(List<StopRequest> sortedStops, TripRequest req) {
        validateCities(sortedStops, req.departureCity(), req.arrivalCity());
        validateUniqueCities(sortedStops);
        validatePrices(sortedStops);
        validateDepartureDateMatchesFirstStop(sortedStops, req);
        //validateTimes(sortedStops);
    }

    private void validateCities(List<StopRequest> sortedStops, String departureCity, String arrivalCity) {
        StopRequest firstStop = sortedStops.get(0);
        StopRequest lastStop = sortedStops.get(sortedStops.size() - 1);

        if (!firstStop.city().equals(departureCity))
            throw new BusinessException("la ville de premier arret doit etre egal la ville de depart");

        if (!lastStop.city().equals(arrivalCity))
            throw new BusinessException("la ville du dernier arret doit etre egal la ville d'arrive");
    }

    private void validateUniqueCities(List<StopRequest> sortedStops) {
        Set<String> uniqueCities = new HashSet<>();

        for (StopRequest stop : sortedStops) {
            if (!uniqueCities.add(stop.city()))
                throw new BusinessException("la ville doit etre unique dans les point arret!!");
        }
    }

    private void validatePrices(List<StopRequest> sortedStops) {
        StopRequest firstStop = sortedStops.get(0);

        if (firstStop.priceFromStart().compareTo(BigDecimal.ZERO) != 0)
            throw new BusinessException("le prix du 1ere arret doit vaut zero 0");


        BigDecimal previousPrice = BigDecimal.valueOf(-1);

        for (StopRequest stop : sortedStops) {

            if (stop.priceFromStart().compareTo(previousPrice) <= 0)
                throw new BusinessException("les prix doit etre srict croissants");


            previousPrice = stop.priceFromStart();

        }
    }

    private void validateDepartureDateMatchesFirstStop(List<StopRequest> sortedStops, TripRequest req) {

        StopRequest firstStop = sortedStops.get(0);
        if (!firstStop.arrivalTime().equals(req.departureCity()))
            throw new BusinessException("date de depart doit etre egal date de premier arret");

    }

    private Trip buildAndSaveTrip(TripRequest req, Vehicle vehicle, List<StopRequest> sortedStops) {

        Trip trip = tripMapper.toEntity(req);
        trip.setStatus(StatutTrajet.PUBLIE);
        trip.setVehicle(vehicle);

        for (StopRequest stopReq : sortedStops) {
            Stop stop = stopMapper.toEntity(stopReq);
            stop.setTrip(trip);
            trip.getStops().add(stop);
        }

        return tripRepository.save(trip);

    }

}
