package com.covoiturage.mapper;

import com.covoiturage.dto.request.PassengerRequest;
import com.covoiturage.dto.response.PassengerResponse;
import com.covoiturage.entity.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PassengerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    Passenger toEntity(PassengerRequest request);

    PassengerResponse toResponse(Passenger passenger);
    List<PassengerResponse> toResponseList(List<Passenger> passengers);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    void updateEntityFromRequest(PassengerRequest request, @MappingTarget Passenger passenger);
}