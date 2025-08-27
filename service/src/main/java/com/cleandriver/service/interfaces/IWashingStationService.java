package com.cleandriver.service.interfaces;

import com.cleandriver.dto.washingstation.WashingStationDto;
import com.cleandriver.model.Appointment;
import com.cleandriver.model.WashingStation;

import java.time.LocalDateTime;
import java.util.List;

public interface IWashingStationService {


    WashingStationDto updateWashingStation(Long washingStationId, WashingStationDto washingStation);

    WashingStationDto enableWashingStation(Long washingId);

    WashingStationDto disableWashingStation(Long washingId);

    void deleteWashingStation(Long washingStationId);

    WashingStationDto findWashingStationDto(Long id);

    WashingStation findWashingStation(Long id);


    List<WashingStation> findAllWashingStations();

    List<WashingStation> getAvailableStations(LocalDateTime startAppointment, LocalDateTime endAppointment);

    List<WashingStationDto> getAvailableStationsDto(LocalDateTime startAppointment, LocalDateTime endAppointment);

    void takeUpStation(Long stationId);

    void setFreeStation(Long stationId);

    WashingStationDto createWashingStation(WashingStationDto serviceType);

    WashingStation resolverWashingStationToWash(Appointment appointment);

    List<WashingStation> getAvailableWashingStationOnAppointment(LocalDateTime startAppointment, LocalDateTime endAppointment);
}
