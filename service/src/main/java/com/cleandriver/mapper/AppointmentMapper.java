package com.cleandriver.mapper;

import com.cleandriver.dto.appointment.AppointmentRequest;
import com.cleandriver.dto.appointment.AppointmentResponse;
import com.cleandriver.model.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class,VehicleMapper.class,ServiceTypeMapper.class})
public interface AppointmentMapper {

    @Mapping(target = "stationName",source = "washingStation.name")
    AppointmentResponse toResponse(Appointment appointment);

//    Appointment toAppointment(AppointmentRequest appointmentRequest);


}
