package com.covoiturage.mapper;

import com.covoiturage.dto.request.StopRequest;
import com.covoiturage.dto.response.StopResponse;
import com.covoiturage.entity.Stop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StopMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trip", ignore = true)
    Stop toEntity(StopRequest request);

    StopResponse toResponse(Stop stop);
    List<StopResponse> toResponseList(List<Stop> stops);
}