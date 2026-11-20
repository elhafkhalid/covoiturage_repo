package com.covoiturage.service.Impl;

import com.covoiturage.dto.request.ReservationRequest;
import com.covoiturage.dto.response.ReservationResponse;
import com.covoiturage.entity.Passenger;
import com.covoiturage.entity.Reservation;
import com.covoiturage.entity.Stop;
import com.covoiturage.entity.Trip;
import com.covoiturage.enums.StatutReservation;
import com.covoiturage.enums.StatutTrajet;
import com.covoiturage.exception.BusinessException;
import com.covoiturage.exception.ResourceNotFoundException;
import com.covoiturage.mapper.ReservationMapper;
import com.covoiturage.repository.PassengerRepository;
import com.covoiturage.repository.ReservationRepository;
import com.covoiturage.repository.StopRepository;
import com.covoiturage.repository.TripRepository;
import com.covoiturage.service.ReservationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final PassengerRepository passengerRepository;
    private final TripRepository tripRepository;
    private final StopRepository stopRepository;

    private final ReservationMapper reservationMapper;


    @Transactional
    public ReservationResponse createReservation(ReservationRequest request) {
        log.info("creation d'1 reservation : passager={}, trajet={}",
                request.passengerId(), request.tripId());

        Passenger passenger = getPassenger(request.passengerId());
        Trip trip = getTrip(request.tripId());
        Stop pickupStop = getStop(request.pickupStopId());
        Stop dropoffStop = getStop(request.dropoffStopId());

        validateTripIsPublished(trip);
        validateStopsBelongToTrip(pickupStop, dropoffStop, trip);
        validateStopOrder(pickupStop, dropoffStop);
        validateAvailableSeats(trip, request.numberOfSeats());

        BigDecimal totalPrice = calculateSegmentPrice(pickupStop, dropoffStop, request.numberOfSeats());

        Reservation reservation = reservationMapper.toEntity(request);

        reservation.setPassenger(passenger);
        reservation.setTrip(trip);
        reservation.setPickupStop(pickupStop);
        reservation.setDropoffStop(dropoffStop);
        reservation.setTotalPrice(totalPrice);
        reservation.setStatus(StatutReservation.CONFIRMEE);
        reservation.setReservationDate(LocalDateTime.now());

        Reservation savedReservation = reservationRepository.save(reservation);

        updateTripStatus(trip);

        return reservationMapper.toResponse(savedReservation);
    }

    @Transactional
    public ReservationResponse getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("reser intrv avec id : " + id));

        return reservationMapper.toResponse(reservation);
    }

    public List<ReservationResponse> getPassengerReservations(Long passengerId) {

        getPassenger(passengerId);
        List<Reservation> reservations = reservationRepository.findByPassengerId(passengerId);

        return reservationMapper.toResponseList(reservations);
    }

    public void cancelReservation(Long id) {

        log.info("Annulation de la réservation ID : {}", id);
        Reservation reservation = getReservation(id);

        if (reservation.getStatus().equals(StatutReservation.CONFIRMEE)) {
            validationDeadline(reservation.getTrip());
            reservation.setStatus(StatutReservation.ANNULLE);
            reservationRepository.save(reservation);

            updateTripStatusAfterCancel(reservation.getTrip());
        }

    }

    // METHODS PRIVE POUR CREATE__RESREVATION

    private Passenger getPassenger(Long passengerId) {
        return passengerRepository.findById(passengerId)
                .orElseThrow(() -> new ResourceNotFoundException("passenfer intrv avec id : " + passengerId));
    }

    private Trip getTrip(Long tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("trip intrv avec id : " + tripId));
    }

    private Stop getStop(Long stopId) {
        return stopRepository.findById(stopId)
                .orElseThrow(() -> new ResourceNotFoundException("point arret intrv avec id : " + stopId));
    }

    private void validateTripIsPublished(Trip trip) {
        if (trip.getStatus() != StatutTrajet.PUBLIE)
            throw new BusinessException("impossible de reserver avec status : " + trip.getStatus());
    }

    private void validateStopsBelongToTrip(Stop pickupStop, Stop dropoffStop, Trip trip) {
        if (!pickupStop.getTrip().getId().equals(trip.getId()))
            throw new BusinessException("ce point de monte n'appartien pas a ce trajet");

        if (!dropoffStop.getTrip().getId().equals(trip.getId()))
            throw new BusinessException("ce point de descend n'appartiet pas a ce trajet");
    }

    private void validateStopOrder(Stop pickupStop, Stop dropoffStop) {
        if (pickupStop.getStopOrder() >= dropoffStop.getStopOrder())
            throw new BusinessException("le point de monte doit avant point de desente");
    }

    private int calculateReservedSeats(Trip trip) {
        return trip.getReservations().stream()
                .filter(r -> r.getStatus().equals(StatutReservation.CONFIRMEE))
                .mapToInt(Reservation::getNumberOfSeats)
                .sum();
    }

    private void validateAvailableSeats(Trip trip, Integer requestSeats) {
        int reserved = calculateReservedSeats(trip);
        int available = trip.getProposedSeats() - reserved;

        if (requestSeats > available)
            throw new BusinessException("pas assez de places dispo");

    }

    private BigDecimal calculateSegmentPrice(Stop pickupStop, Stop dropoffStop, Integer numberOfSeats) {
        BigDecimal pricePerSeat = dropoffStop.getPriceFromStart().subtract(pickupStop.getPriceFromStart());
        return pricePerSeat.multiply(BigDecimal.valueOf(numberOfSeats));
    }

    private void updateTripStatus(Trip trip) {
        int reserved = calculateReservedSeats(trip);
        int available = trip.getProposedSeats() - reserved;

        if (available == 0 && trip.getStatus().equals(StatutTrajet.PUBLIE))
            trip.setStatus(StatutTrajet.COMPLET);

        tripRepository.save(trip);
    }

    // METHODS PRIVE POUR CANCEL_RESERVATION

    private Reservation getReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("reservation intrv avec id : " + id));
    }

    private void validationDeadline(Trip trip) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime departureDate = trip.getDepartureDate();

        long hoursUntilDeparture = Duration.between(now, departureDate).toHours();

        if (hoursUntilDeparture < 24)
            throw new BusinessException("impossible anuule il faut le depart que dans moins de 24h");

    }

    private void updateTripStatusAfterCancel(Trip trip) {
        if (trip.getStatus().equals(StatutTrajet.COMPLET)) {
            trip.setStatus(StatutTrajet.PUBLIE);
            tripRepository.save(trip);
        }


    }


}
