package com.cleandriver.service.implement;

import com.cleandriver.dto.wash.WashRequest;
import com.cleandriver.dto.wash.WashResponse;
import com.cleandriver.exception.generalExceptions.ResourceNotFoundException;
import com.cleandriver.mapper.WashMapper;
import com.cleandriver.mapper.WashingStationMapper;
import com.cleandriver.model.Appointment;
import com.cleandriver.model.Employed;
import com.cleandriver.model.Wash;
import com.cleandriver.model.WashingStation;
import com.cleandriver.model.enums.WashStatus;
import com.cleandriver.persistence.WashRepository;
import com.cleandriver.service.interfaces.appointment.IAppointmentService;
import com.cleandriver.service.interfaces.IEmployedService;
import com.cleandriver.service.interfaces.IWashService;
import com.cleandriver.service.interfaces.IWashingStationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WashService implements IWashService {


    @Autowired
    private WashRepository washRepository;
    @Autowired
    private IAppointmentService appointmentService;
    @Autowired
    private IWashingStationService washingStationService;
    @Autowired
    private WashingStationMapper washingStationMapper;
    @Autowired
    private IEmployedService employedService;

    @Autowired
    private WashMapper washMapper;






    @Override
    @Transactional
    public WashResponse initWash(WashRequest washRequest) {

        Appointment appointment = appointmentService.findAppointmentToWash(washRequest.getAppointmentId());

        WashingStation washingStation = washingStationService.resolverWashingStation(washRequest.getWashingStationId(),appointment);

        Employed employed = employedService.findEmployedToWash(washRequest.getEmployedId());

        appointmentService.initAppointment(appointment.getId());

        washingStationService.takeUpStation(washingStation.getId());

        Wash wash = Wash.builder()
                .washingStation(washingStation)
                .appointment(appointment)
                .employed(employed)
                .initAt(LocalDateTime.now())
                .status(WashStatus.IN_PROCESS)
                .build();

        return washMapper.toResponse(washRepository.save(wash));
    }

    @Override
    @Transactional
    public WashResponse endWash(Long washId) {

        Wash wash = this.findWash(washId);

        appointmentService.finishAppointment(wash.getAppointment().getId());

        washingStationService.setFreeStation(wash.getWashingStation().getId());

        wash.setEndAt(LocalDateTime.now());

        wash.setStatus(WashStatus.COMPLETED);

        return washMapper.toResponse(washRepository.save(wash));
    }

    @Override
    @Transactional
    public WashResponse cancelWash(Long washId) {
        Wash wash = this.findWash(washId);

        appointmentService.cancelAppointment(wash.getAppointment().getId());

        washingStationService.setFreeStation(wash.getWashingStation().getId());

        wash.setEndAt(LocalDateTime.now());

        wash.setStatus(WashStatus.CANCELED);

        return washMapper.toResponse(washRepository.save(wash));
    }

    private Wash findWash(Long washId){
        return washRepository.findById(washId).orElseThrow(
                () -> new ResourceNotFoundException("No se encontro wash con id: " +  washId)
        );
    }




}
