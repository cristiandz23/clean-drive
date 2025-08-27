package com.cleandriver.persistence;

import com.cleandriver.model.Appointment;
import com.cleandriver.model.WashingStation;
import com.cleandriver.model.enums.AppointmentStatus;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {


    @Query(value = "SELECT ap.* FROM appointment AS ap " +
            "JOIN customer AS cu ON cu.customer_id = ap.customer_id " +
            "WHERE cu.dni = :customerDni ",nativeQuery = true)
    List<Appointment> findAppointmentByCustomer(@Param("customerDni") String customerDni);

    @Query(value = "SELECT ap.* FROM appointment AS ap " +
            "JOIN vehicle AS ve ON ve.vehicle_id = ap.vehicle_id " +
            "WHERE ve.plate_number = :plateNumber ",nativeQuery = true)
    List<Appointment> findAppointmentByPlateNumber(@Param("plateNumber") String plateNumber);

    List<Appointment> findAllByStartDateTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT * FROM appointment WHERE " +
            "DATE(start_date_time) = CURDATE() " +
            "AND appointment_status IN ('CONFIRMED','IN_PROGRESS','COMPLETED','NO_SHOW')", nativeQuery = true)
    List<Appointment> findAllOfToday();



    @Query(value = "SELECT ap.* FROM appointment ap " +
            "JOIN appointment_promotion app ON ap.appointment_id = app.appointment_id " +
            "JOIN vehicle ve ON ap.vehicle_id = ve.vehicle_id " +
            "WHERE ap.appointment_status IN ('COMPLETED','CONFIRMED') " +
            "AND ve.plate_number = :plateNumber " +
            "AND ap.start_date_time BETWEEN :startDate AND :endDate " +
            "AND app.promotion_id = :promotionId ",

            nativeQuery = true)
    List<Appointment> findCompletedAppointmentsByPlateAndDateRange(
            @Param("plateNumber") String plateNumber,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("promotionId") Long promotionId
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

    @Query("SELECT COUNT(ap)>0 FROM appointment ap " +
            "WHERE ap.serviceType.id = :serviceId " +
            "AND ap.status IN ('CONFIRMED','CREATED') ")
    boolean existsAppointmentWithServiceType(@Param("serviceId") Long serviceId);



    @Query(
            value = "SELECT COUNT(ap)>0 FROM appointment ap " +
                    "WHERE ap.status IN ('CONFIRMED','IN_PROGRESS','NO_SHOW')"
    )
    boolean customerHasPendingAppointment(Long customerId);

    @Modifying
    @Query(value = "UPDATE appointment a SET a.customer_id = NULL WHERE a.customer_id = :customerId",
    nativeQuery = true)
    void detachCustomerFromAppointments(@Param("customerId") Long customerId);

//    @Modifying
//    @Query(value = "UPDATE appointment a SET a.customer_id = NULL WHERE a.customer_id = :customerId",
//            nativeQuery = true)
//    void detachCustomerFromAppointments(@Param("customerId") Long customerId);
//
}
