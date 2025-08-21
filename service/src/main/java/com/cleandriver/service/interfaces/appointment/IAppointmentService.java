package com.cleandriver.service.interfaces.appointment;

import com.cleandriver.dto.appointment.AppointmentRequest;
import com.cleandriver.dto.appointment.AppointmentResponse;
import com.cleandriver.dto.appointment.ExpressAppointmentRequest;
import com.cleandriver.model.Appointment;
import com.cleandriver.model.WashingStation;
import com.cleandriver.model.enums.AppointmentStatus;

import java.time.LocalDateTime;

public interface IAppointmentService {


    AppointmentResponse findAppointment(Long appointmentId);

    Appointment findAppointmentToWash(Long appointmentId);

    AppointmentResponse confirmAndSelectWashingStation(Long appointmentId);

    AppointmentResponse createAppointment(AppointmentRequest appointmentRequest);

    AppointmentResponse createExpressAppointment(ExpressAppointmentRequest appointmentRequest);

    void deleteAppointment(Long appointmentId);

    AppointmentResponse confirmAppointment(Long appointmentId);

    void cancelAppointment(Long appointmentId);

    void initAppointment(Long appointmentId);

    void finishAppointment(Long appointmentId);


    AppointmentResponse updateAppointment(Long appointmentId,AppointmentRequest appointmentRequest);

    void validateTransition(AppointmentStatus current, AppointmentStatus next);


}
