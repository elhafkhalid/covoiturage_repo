package com.covoiturage.service.impl;

import com.covoiturage.dto.request.ReservationRequest;
import com.covoiturage.dto.response.ReservationResponse;
import com.covoiturage.entity.Passenger;
import com.covoiturage.entity.Reservation;
import com.covoiturage.entity.Stop;
import com.covoiturage.entity.Trip;
import com.covoiturage.enums.StatutReservation;
import com.covoiturage.enums.StatutTrajet;
import com.covoiturage.mapper.ReservationMapper;
import com.covoiturage.repository.PassengerRepository;
import com.covoiturage.repository.ReservationRepository;
import com.covoiturage.repository.StopRepository;
import com.covoiturage.repository.TripRepository;
import com.covoiturage.service.Impl.ReservationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest {
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private TripRepository tripRepository;
    @Mock
    private StopRepository stopRepository;
    @Mock
    private ReservationMapper reservationMapper;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Test
    void shouldCrateReservationSuccessfully() {
        Passenger passenger = new Passenger();
        passenger.setId(1L);

        Trip trip = new Trip();
        trip.setId(1L);
        trip.setProposedSeats(3);
        trip.setStatus(StatutTrajet.PUBLIE);
        trip.setReservations(new ArrayList<>());

        Stop pickupStop = new Stop();
        pickupStop.setId(3L);
        pickupStop.setStopOrder(2);
        pickupStop.setPriceFromStart(new BigDecimal("70"));
        pickupStop.setTrip(trip);


        Stop dropoffStop = new Stop();
        dropoffStop.setId(4L);
        dropoffStop.setStopOrder(3);
        dropoffStop.setPriceFromStart(new BigDecimal("100"));
        dropoffStop.setTrip(trip);

        ReservationRequest req = new ReservationRequest(1l, 1L, 3L, 4L, 1);

        Reservation mockReservation = new Reservation();
        mockReservation.setStatus(StatutReservation.CONFIRMEE);
        mockReservation.setNumberOfSeats(1);

        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));
        when(tripRepository.findById(1L)).thenReturn(Optional.of(trip));
        when(stopRepository.findById(3L)).thenReturn(Optional.of(pickupStop));
        when(stopRepository.findById(4L)).thenReturn(Optional.of(dropoffStop));

        when(reservationMapper.toEntity(req)).thenReturn(mockReservation);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(mockReservation);

        ReservationResponse expectedResp = new ReservationResponse(
                1L,
                1,
                LocalDateTime.now(),
                StatutReservation.CONFIRMEE,
                new BigDecimal("30"),
                1L,
                1L,
                "settat",
                "marrakech"
        );

        when(reservationMapper.toResponse(any(Reservation.class))).thenReturn(expectedResp);

        ReservationResponse result = reservationService.createReservation(req);


        assertThat(result).isNotNull();
        assertThat(result.totalPrice()).isEqualByComparingTo("30");
        assertThat(result.status()).isEqualTo(StatutReservation.CONFIRMEE);

    }


}
