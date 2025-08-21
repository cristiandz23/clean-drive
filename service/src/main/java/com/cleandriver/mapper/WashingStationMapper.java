package com.cleandriver.mapper;

import com.cleandriver.dto.washingstation.WashingStationDto;
import com.cleandriver.model.WashingStation;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface WashingStationMapper {

    WashingStation toWashingStation(WashingStationDto washingStation);

    WashingStationDto toDto(WashingStation washingStation);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUpdate(WashingStationDto washingStationDto, @MappingTarget WashingStation washingStation);
}
