package com.cleandriver.mapper;

import com.cleandriver.dto.washingstation.WashingStationDto;
import com.cleandriver.model.WashingStation;

public interface WashingStationMapper {

    WashingStation toWashingStation(WashingStationDto washingStation);

    WashingStationDto toDto(WashingStation washingStation);

}
