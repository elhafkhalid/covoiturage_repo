package com.covoiturage.mapper;

import com.covoiturage.dto.request.DriverRequest;
import com.covoiturage.dto.response.DriverResponse;
import com.covoiturage.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DriverMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    Driver toEntity(DriverRequest request);

    DriverResponse toResponse(Driver driver);

    List<DriverResponse> toResponseList(List<Driver> drivers);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    void updateEntityFromRequest(DriverRequest request, @MappingTarget Driver driver);
}