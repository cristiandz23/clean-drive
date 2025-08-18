package com.cleandriver.service.interfaces.appointment;

import com.cleandriver.dto.appointment.AppointmentResponse;
import com.cleandriver.model.enums.AppointmentStatus;

import java.time.LocalDate;
import java.util.List;

public interface IAppointmentStatsService {

    int getWashAmountByDateAndPlantNumber(String plateNumber, int weekRange);

    List<AppointmentResponse> findTodayAppointments();

    List<AppointmentResponse> getAppointmentsByDate(LocalDate date);

    List<AppointmentResponse> getAppointmentByPlateNumber(String plateNumber);

    List<AppointmentResponse> getAppointmentByCustomer(String customerDni);

    List<AppointmentResponse> getAppointmentByCustomer(String customerDni, AppointmentStatus appointmentStatus);

}
