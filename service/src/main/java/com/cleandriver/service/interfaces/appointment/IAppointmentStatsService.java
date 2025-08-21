package com.cleandriver.service.interfaces.appointment;

import com.cleandriver.dto.appointment.AppointmentResponse;
import com.cleandriver.model.enums.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentStatsService {

    int getCompletedAppointmentsByPlateAndDateRange(String plateNumber, int weekRange, LocalDateTime startDate,Long appointmentPromotionId);

    int getCompletedAppointmentsByPlateAndDateRangeAndService(String plateNumber, int weekRange, LocalDateTime startDate,Long appointmentPromotionId, Long serviceType);


    List<AppointmentResponse> findTodayAppointments();

    List<AppointmentResponse> getAppointmentsByDate(LocalDate date);

    List<AppointmentResponse> getAppointmentByPlateNumber(String plateNumber);

    List<AppointmentResponse> getAppointmentByCustomer(String customerDni);

    List<AppointmentResponse> getAppointmentByCustomer(String customerDni, AppointmentStatus appointmentStatus);

    boolean existsAppointmentWithServiceType(Long serviceTypeId);


}
