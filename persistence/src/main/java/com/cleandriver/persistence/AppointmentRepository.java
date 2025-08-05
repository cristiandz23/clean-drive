package com.cleandriver.persistence;

import com.cleandriver.model.Appointment;
import com.cleandriver.model.WashingStation;
import com.cleandriver.model.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    List<Appointment> findAllByStatus(AppointmentStatus status);

    List<Appointment> findAllByStatusAndCustomer_Dni(AppointmentStatus status, String dni);

    List<Appointment> findAllByCustomer_Dni(String customerDni);

    List<Appointment> findAllByDateTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT a FROM Appointment a WHERE a.washingStation = :station AND " +
            "(:start < a.endTime AND :end > a.startTime)")
    List<Appointment> findByWashingStationAndOverlapping(@Param("station") WashingStation station,
                                                         @Param("start") LocalDateTime start,
                                                         @Param("end") LocalDateTime end);

    List<Appointment> findByVehicleToWash_PlateNumberAndStartDateTimeAfter(
            String plateNumber,
            LocalDateTime after
    );

}
