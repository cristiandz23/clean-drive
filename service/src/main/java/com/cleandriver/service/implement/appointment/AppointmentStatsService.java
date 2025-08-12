package com.cleandriver.service.implement.appointment;

import com.cleandriver.dto.appointment.AppointmentResponse;
import com.cleandriver.mapper.AppointmentMapper;
import com.cleandriver.model.enums.AppointmentStatus;
import com.cleandriver.persistence.AppointmentRepository;
import com.cleandriver.service.interfaces.appointment.IAppointmentStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class AppointmentStatsService implements IAppointmentStatsService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public int getWashAmountByDateAndPlantNumber(String plateNumber, int weekRange) {
        return appointmentRepository.findAppointmentsToPlateNumbersAndStartDateTime(plateNumber,AppointmentStatus.COMPLETED,
                        LocalDateTime.now().minusWeeks(weekRange)).stream()
                .toList().size();
    }

    @Override
    public List<AppointmentResponse> findTodayAppointments() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1); // inicio del día siguiente

        return appointmentRepository.findAllByStartDateTimeBetween(start, end)
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    @Override
    public List<AppointmentResponse> findAppointmentsByDate(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = start.plusDays(1); // Fin del día

        return appointmentRepository.findAllByStartDateTimeBetween(start, end)
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    @Override
    public List<AppointmentResponse> findCustomerAppointments(String customerDni) {

        return appointmentRepository.findAllByCustomer_Dni(customerDni)
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    @Override
    public List<AppointmentResponse> findCustomerAppointments(String customerDni, AppointmentStatus appointmentStatus) {

        return appointmentRepository.findAllByCustomer_Dni(customerDni)
                .stream()
                .filter(appointment -> appointment.getStatus().equals(appointmentStatus))
                .map(appointmentMapper::toResponse)
                .toList();
    }

}
