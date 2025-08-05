package com.cleandriver.service.interfaces;

import com.cleandriver.dto.washingstation.WashingStationDto;
import com.cleandriver.model.WashingStation;

import java.time.LocalDateTime;
import java.util.List;

public interface IWashingStationService {


    WashingStationDto findWashingStationDto(Long id);

    WashingStation findWashingStation(Long id);


    List<WashingStation> findAllWashingStations();

//    List<WashingStationResponse> findWashingStationWhitAppointment(LocalDateTime start,LocalDateTime end);
    List<WashingStation> getAvailableStations(LocalDateTime startAppointment, LocalDateTime endAppointment);

    List<WashingStationDto> getAvailableStationsDto(LocalDateTime startAppointment, LocalDateTime endAppointment);

    void takeUpStation(Long stationId);

    void setFreeStation(Long stationId);

}
