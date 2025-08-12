package com.cleandriver.service.interfaces.appointment;

import com.cleandriver.dto.appointment.AppointmentResponse;
import com.cleandriver.model.enums.AppointmentStatus;

import java.time.LocalDate;
import java.util.List;

public interface IAppointmentStatsService {

    int getWashAmountByDateAndPlantNumber(String plateNumber, int weekRange);

    List<AppointmentResponse> findTodayAppointments();

    List<AppointmentResponse> findAppointmentsByDate(LocalDate date);

    List<AppointmentResponse> findCustomerAppointments(String customerDni);

    List<AppointmentResponse> findCustomerAppointments(String customerDni, AppointmentStatus appointmentStatus);

}
