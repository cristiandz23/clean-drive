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
        if(washingStationRepository.count()<1)
            throw new RuntimeException("No hay estacines cargadas en la base de datos");

        List<WashingStation> washingStations = washingStationRepository.findAvailableStations(startAppointment,endAppointment);
        if(washingStations.isEmpty())
            throw new RuntimeException("No hay washing stations disponibles en el rango horario");
        return washingStations;
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
        washingStation.setBusy(false);
        washingStationRepository.save(washingStation);
    }

    @Override
    public WashingStationDto createWashingStation(WashingStationDto washingStation) {

        WashingStation newWashingStation = washingStationMapper.toWashingStation(washingStation);

        washingStation.setBusy(false);

        return washingStationMapper.toDto(washingStationRepository.save(newWashingStation));
    }

    @Override
    public WashingStation findWashingStation(Long id){
      return washingStationRepository.findById(id).orElseThrow(
              () -> new RuntimeException("No se encontro washing station con id: " + id)
      );
    }
}
