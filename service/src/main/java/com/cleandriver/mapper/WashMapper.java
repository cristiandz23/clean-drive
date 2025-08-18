package com.cleandriver.mapper;

import com.cleandriver.dto.wash.WashResponse;
import com.cleandriver.model.Wash;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WashMapper {

    @Mapping(source = "appointment.id", target = "appointmentId")
    @Mapping(source = "wash.washingStation.name",target = "washingStation")
    @Mapping(source = "employed.name" , target = "employed")
    WashResponse toResponse(Wash wash);

}
