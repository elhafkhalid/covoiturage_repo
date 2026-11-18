package com.covoiturage.mapper;

import com.covoiturage.dto.request.VehicleRequest;
import com.covoiturage.dto.response.VehicleResponse;
import com.covoiturage.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "driver", ignore = true)
    Vehicle toEntity(VehicleRequest request);

    @Mapping(source = "driver.id", target = "driverId")
    VehicleResponse toResponse(Vehicle vehicle);

    List<VehicleResponse> toResponseList(List<Vehicle> vehicles);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "driver", ignore = true)
    void updateEntityFromRequest(VehicleRequest request, @MappingTarget Vehicle vehicle);
}