package com.covoiturage.mapper;

import com.covoiturage.dto.request.ReservationRequest;
import com.covoiturage.dto.response.ReservationResponse;
import com.covoiturage.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "passenger", ignore = true)
    @Mapping(target = "trip", ignore = true)
    @Mapping(target = "pickupStop", ignore = true)
    @Mapping(target = "dropoffStop", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "reservationDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    Reservation toEntity(ReservationRequest request);

    @Mapping(source = "passenger.id", target = "passengerId")
    @Mapping(source = "passenger.nom", target = "passengerNom")
    @Mapping(source = "trip.id", target = "tripId")
    @Mapping(source = "pickupStop.city", target = "pickupCity")
    @Mapping(source = "dropoffStop.city", target = "dropoffCity")
    ReservationResponse toResponse(Reservation reservation);

    List<ReservationResponse> toResponseList(List<Reservation> reservations);
}