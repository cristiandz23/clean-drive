package com.cleandriver.service.implement;

import com.cleandriver.dto.washingstation.WashingStationDto;
import com.cleandriver.mapper.WashingStationMapper;
import com.cleandriver.model.WashingStation;
import com.cleandriver.persistence.WashingStationRepository;
import com.cleandriver.service.interfaces.IWashingStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WashingStationService implements IWashingStationService {

    @Autowired
    private WashingStationMapper washingStationMapper;
    @Autowired
    private WashingStationRepository washingStationRepository;

    @Override
    public WashingStationDto findWashingStationDto(Long id) {
        return washingStationMapper.toDto(findWashingStation(id));
    }

    @Override
    public List<WashingStation> findAllWashingStations() {
        return List.of();
    }

//    @Override
//    public List<WashingStationResponse> findWashingStationWhitAppointment(LocalDateTime start, LocalDateTime end) {
//        return List.of();
//    }

    @Override
    public List<WashingStationDto> getAvailableStationsDto(LocalDateTime startAppointment, LocalDateTime endAppointment) {
        return getAvailableStations(startAppointment,endAppointment)
                .stream()
                .map(washingStationMapper::toDto)
                .toList();
    }

    @Override
    public List<WashingStation> getAvailableStations(LocalDateTime startAppointment, LocalDateTime endAppointment) {
        return List.of();
    }

    @Override
    public void takeUpStation(Long stationId) {
        WashingStation washingStation = this.findWashingStation(stationId);
        washingStation.setBusy(true);
        washingStationRepository.save(washingStation);
    }

    @Override
    public void setFreeStation(Long stationId) {
        WashingStation washingStation = this.findWashingStation(stationId);
        washingStation.setBusy(falsen);
        washingStationRepository.save(washingStation);
    }

    @Override
    public WashingStation findWashingStation(Long id){
      return washingStationRepository.findById(id).orElseThrow(
              () -> new RuntimeException("No se encontro washing station con id: " + id)
      );
    }
}
