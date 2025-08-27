package com.cleandriver.service.implement;

import com.cleandriver.dto.washingstation.WashingStationDto;
import com.cleandriver.exception.generalExceptions.ResourceNotFoundException;
import com.cleandriver.exception.washingStationException.NotAvailableWashingStationException;
import com.cleandriver.mapper.WashingStationMapper;
import com.cleandriver.model.Appointment;
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
    public WashingStationDto updateWashingStation(Long washingStationId, WashingStationDto washingStation) {

        WashingStation washing = this.findWashingStation(washingStationId);

        washingStationMapper.toUpdate(washingStation,washing);

        return washingStationMapper.toDto(washingStationRepository.save(washing));
    }

    @Override
    public WashingStationDto enableWashingStation(Long washingId) {

        WashingStation washingStation = this.findWashingStation(washingId);
        washingStation.setAvailable(true);
        return washingStationMapper.toDto(washingStationRepository.save(washingStation));
    }

    @Override
    public WashingStationDto disableWashingStation(Long washingId) {
        if(washingStationRepository.isInProcess(washingId))
            throw new RuntimeException("Hay turnos por resolver no puede desactivar la estacion");
        WashingStation washingStation = this.findWashingStation(washingId);
        washingStation.setAvailable(false);
        return washingStationMapper.toDto(washingStationRepository.save(washingStation));
    }

    @Override
    public void deleteWashingStation(Long washingStationId) {

        WashingStation washingStation = this.findWashingStation(washingStationId);
        if(washingStationRepository.isInProcess(washingStationId))
            throw new RuntimeException("Tiene turnos pendientes, no se pude eliminar");
        washingStationRepository.delete(washingStation);
    }

    @Override
    public WashingStationDto findWashingStationDto(Long id) {
        return washingStationMapper.toDto(findWashingStation(id));
    }

    @Override
    public List<WashingStation> findAllWashingStations() {
        return List.of();
    }


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
            throw new NotAvailableWashingStationException("No hay estacines cargadas en la base de datos");

        List<WashingStation> washingStations = washingStationRepository.findAvailableStations(startAppointment,endAppointment); // descomentar la linea que hace que los turnos deban estar pagados

        if(washingStations.isEmpty())

            throw new NotAvailableWashingStationException("No hay washing stations disponibles en el rango horario");

        return washingStations;
    }

    @Override
    public void takeUpStation(Long stationId) {
        WashingStation washingStation = this.findWashingStation(stationId);
        if(washingStation.isBusy())
            throw new RuntimeException("La estacion esta ocupada");
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
              () -> new ResourceNotFoundException("No se encontro washing station con id: " + id)
      );
    }

    @Override
    public WashingStation resolverWashingStationToWash(Appointment appointment){

        WashingStation washingStation = this.findWashingStation(appointment.getWashingStation().getId());

        int appointmentDuration = appointment.getServiceType().getDurationInMinutes();

        List<WashingStation> washingStations = this.getAvailableStations(LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(appointmentDuration));

        if(washingStations.isEmpty())
            throw new NotAvailableWashingStationException("No hay estaciones de lavado disponibles");

        washingStations = washingStations.stream().filter( w -> !w.isBusy()).toList();

        if(washingStations.isEmpty())
            throw new NotAvailableWashingStationException("Hay disponibles pero estan marcadas como ocupadas");


        if(washingStations.contains(washingStation))
            return washingStation;

        return washingStations.getFirst();

    }

    @Override
    public List<WashingStation> getAvailableWashingStationOnAppointment(LocalDateTime startAppointment, LocalDateTime endAppointment) {
        return washingStationRepository.findAvailableStations(startAppointment,endAppointment);
    }



}
