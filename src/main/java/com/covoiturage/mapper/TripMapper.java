package com.covoiturage.mapper;

import com.covoiturage.dto.request.TripRequest;
import com.covoiturage.dto.response.TripResponse;
import com.covoiturage.entity.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {StopMapper.class})
public interface TripMapper {

    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "stops", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "id", ignore = true)
    Trip toEntity(TripRequest request);

    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "vehicle.brand", target = "vehicleBrand")
    @Mapping(target = "availableSeats", ignore = true)
    TripResponse toResponse(Trip trip);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "stops", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntityFromRequest(TripRequest request, @MappingTarget Trip trip);
}