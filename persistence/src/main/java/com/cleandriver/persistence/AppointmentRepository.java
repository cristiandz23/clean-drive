package com.cleandriver.persistence;

import com.cleandriver.model.Appointment;
import com.cleandriver.model.WashingStation;
import com.cleandriver.model.enums.AppointmentStatus;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

//    List<Appointment> findAllByStatus(AppointmentStatus status);

//    List<Appointment> findAllByStatusAndCustomer_Dni(AppointmentStatus status, String dni);

    @Query(value = "SELECT ap.* FROM appointment AS ap " +
            "JOIN customer AS cu ON cu.customer_id = ap.customer_id " +
            "WHERE cu.dni = :customerDni ",nativeQuery = true)
    List<Appointment> findAppointmentByCustomer(@Param("customerDni") String customerDni);

    @Query(value = "SELECT ap.* FROM appointment AS ap " +
            "JOIN vehicle AS ve ON ve.vehicle_id = ap.vehicle_id " +
            "WHERE ve.plate_number = :plateNumber ",nativeQuery = true)
    List<Appointment> findAppointmentByPlateNumber(@Param("plateNumber") String plateNumber);
//    List<Appointment> findAllByDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Appointment> findAllByStartDateTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT * FROM appointment WHERE " +
            "DATE(start_date_time) = CURDATE() " +
            "AND appointment_status IN ('CONFIRMED','IN_PROGRESS','COMPLETED','NO_SHOW')", nativeQuery = true)
    List<Appointment> findAllOfToday();



//    @Query("SELECT a FROM Appointment a WHERE a.washingStation = :station AND " +
//            "(:start < a.endTime AND :end > a.startTime)")
//    List<Appointment> findByWashingStationAndOverlapping(@Param("station") WashingStation station,
//                                                         @Param("start") LocalDateTime start,
//                                                         @Param("end") LocalDateTime end);

    List<Appointment> findByVehicleToWash_PlateNumberAndStartDateTimeAfter(
            String plateNumber,
            LocalDateTime after
    );

    @Query(value = "SELECT * FROM appointment AS ap" +
            "JOIN vehicle AS ve ON ap.vehicle_id = ve.vehicle_id" +
            "WHERE ap.appointment_status = :status" +
            "AND ve.plate_number = :plateNumber" +
            "AND ap.star_date_time = :after",
            nativeQuery = true

    )
    List<Appointment> findAppointmentsToPlateNumbersAndStartDateTime(
            @Param("plateNumber") String plateNumber,
            @Param("status") AppointmentStatus status,
            @Param("after") LocalDateTime after
    );


    @Query(value = "SELECT a.appointment_id FROM appointment AS a WHERE " +
//            "JOIN vehicle AS ve ON a.vehicle_id = ve.vehicle_id " +
//            "WHERE ve.plate_number = :plateNumber AND " +
            "a.start_date_time = :start", nativeQuery = true)
    Long getAppointmentIdAt(@Param("start") LocalDateTime start);
//                            @Param("plateNumber") String plateNumber);

    @Query(value = "SELECT " +
                "CASE " +
                    "WHEN COUNT(*) > 0 THEN true " +
                "ELSE false " +
            "END " +
            "FROM appointment AS a " +
            "JOIN vehicle AS ve ON a.vehicle_id = ve.vehicle_id " +
            "WHERE ve.plate_number = :plateNumber "+
            "AND a.start_date_time < :end " +
            "AND a.end_date_time > :start " +
            "AND a.appointment_status IN ('CREATED', 'CONFIRMED')",
            nativeQuery = true
            )
    Long hasAppointmentInRangeByVehiclePlate(@Param("plateNumber") String plateNumber,
                                              @Param("start") LocalDateTime start,
                                              @Param("end") LocalDateTime end);
    //SI DEVUELVE 1 ESE AUTO YA TIENE UN TURNO EN ESE RANGO HORARIO, SI DEVULUELVE 0 ESE AUTO NO TIENE TURNOS
    //EN ESE RANGO HORARIO Y PUEDE TOMAR UN TURNO

//    @Query(value = "SELECT COUNT(ap) > FROM A ppointment ap " +
//            "WHERE ap.serviceType.id = :serviceId " +
//            "AND ap.status IN ('CONFIRMED','CREATED') ")
//    boolean existsAppointmentWithServiceType(@Param("serviceId") Long serviceId);

}
