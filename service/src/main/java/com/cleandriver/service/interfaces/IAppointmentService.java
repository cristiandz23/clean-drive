package com.cleandriver.service.interfaces;

import com.cleandriver.dto.appointment.AppointmentRequest;
import com.cleandriver.dto.appointment.AppointmentResponse;
import com.cleandriver.dto.appointment.ExpressAppointmentRequest;
import com.cleandriver.model.Appointment;
import com.cleandriver.model.enums.AppointmentStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {


    AppointmentResponse findAppointment(Long appointmentId);

    Appointment findAppointmentToWash(Long appointmentId);

    List<AppointmentResponse> findTodayAppointments();

    List<AppointmentResponse> findAppointmentsByDate(LocalDate date);

    List<AppointmentResponse> findCustomerAppointments(String customerDni);

    List<AppointmentResponse> findCustomerAppointments(String customerDni, AppointmentStatus appointmentStatus);

    AppointmentResponse createAppointment(AppointmentRequest appointmentRequest);

    AppointmentResponse createExpressAppointment(ExpressAppointmentRequest appointmentRequest);

    void deleteAppointment(Long appointmentId);

    AppointmentResponse confirmAppointment(Long appointmentId);

    void cancelAppointment(Long appointmentId);

    void initAppointment(Long appointmentId);

    void finishAppointment(Long appointmentId);

    AppointmentResponse updateAppointment(Long appointmentId,AppointmentRequest appointmentRequest);

//    int amountWashByCarAndTime(String plateNumber, LocalDateTime time);

    int getWashAmountByDateAndPlantNumber(String plateNumber, int weekRange);
    
}
