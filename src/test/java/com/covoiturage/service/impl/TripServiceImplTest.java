package com.covoiturage.service.impl;

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
import com.covoiturage.service.Impl.TripServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class TripServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private TripRepository tripRepository;
    @Mock
    private TripMapper tripMapper;
    @Mock
    private StopMapper stopMapper;

    @InjectMocks
    private TripServiceImpl tripService;

    private Vehicle vehicle;
    private TripRequest validReq;
    private Trip trip;
    private TripResponse expectedResp;

    @BeforeEach
    void setup() {

        vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setCapacity(4);

        LocalDateTime departureDate = LocalDateTime.now().plusHours(1);

        List<StopRequest> stops = List.of(
                new StopRequest("rabat", 0, departureDate, BigDecimal.ZERO),
                new StopRequest("casa", 1, departureDate.plusHours(2), new BigDecimal("40")),
                new StopRequest("marrakech", 2, departureDate.plusHours(3), new BigDecimal("100"))
        );

        validReq = new TripRequest(
                departureDate,
                "rabat",
                "marrakech",
                3,
                1L,
                stops
        );

        trip = new Trip();
        trip.setId(1L);
        trip.setStatus(StatutTrajet.PUBLIE);

        expectedResp = new TripResponse(
                1l,
                LocalDateTime.now().plusHours(1),
                "rabat",
                "marrakech",
                3,
                3,
                StatutTrajet.PUBLIE,
                1l,
                List.of()
        );
    }


    @Test
    void shouldCreateTripSuccessfully() {

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(tripMapper.toEntity(validReq)).thenReturn(trip);
        when(tripRepository.save(trip)).thenReturn(trip);
        when(tripMapper.toResponse(trip)).thenReturn(expectedResp);
        when(stopMapper.toEntity(any(StopRequest.class))).thenReturn(new Stop());

        TripResponse result = tripService.createTrip(validReq);

        assertThat(result.status()).isEqualTo(StatutTrajet.PUBLIE);
    }

    @Test
    void shouldThrowWhenVehicleNotFound() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tripService.createTrip(validReq))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("vehicle intrv avec id");
    }

    @Test
    void shouldThrowWhenCapacityExceeded() {

        TripRequest invalidReq = new TripRequest(
                validReq.departureDate(),
                validReq.departureCity(),
                validReq.arrivalCity(),
                5,
                validReq.vehicleId(),
                validReq.stops()
        );

        when(vehicleRepository.findById(validReq.vehicleId())).thenReturn(Optional.of(vehicle));

        assertThatThrownBy(() -> tripService.createTrip(invalidReq))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("le nmbres propose ne doit pas deppaser capacity");

    }


}
