package com.cleandriver.persistence;

import com.cleandriver.model.Wash;
import jakarta.persistence.NamedNativeQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.util.List;

@Repository
public interface WashRepository extends JpaRepository<Wash,Long> {


//    @Query(value = "SELECT " +
//            "CASE " +
//            "   WHEN COUNT(*) > 0 THEN TRUE" +
//            "   ELSE FALSE" +
//            "END " +
//            " FROM wash AS a " +
//            "JOIN appointment AS ap ON a.appointment_id = ap.appointment_id " +
//            "JOIN vehicle AS v ON ap.vehicle_id = v.vehicle_id " +
//            "WHERE v.plate_number = :plateNumber " +
//            "AND a.status= 'IN_PROCESS' ", nativeQuery=true)
//    boolean isVehicleBeingWashed(@Param("plateNumber") String numberPlate);

    @Query(value = "SELECT COUNT(*) > 0 " +
            "FROM wash w " +
            "JOIN w.appointment ap " +
            "JOIN ap.vehicleToWash v " +
            "WHERE v.plateNumber = :plateNumber " +
            "AND w.status = 'IN_PROCESS'")
    boolean isVehicleBeingWashed(@Param("plateNumber") String numberPlate);

    @Query(value = "SELECT w FROM wash w " +
            "WHERE w.status = 'IN_PROCESS'")
    List<Wash> getCurrentWashes();

    @Query(value = "SELECT * FROM wash AS w WHERE DATE(w.init_at) = CURDATE()", nativeQuery = true)
    List<Wash> getWashesToday();

    @Query(value = "SELECT * FROM wash AS w WHERE DATE(w.init_at) = DATE( :initAt );",nativeQuery = true)
    List<Wash> getWashesFromDay(@Param("initAt") LocalDate initAt);

    @Query(value = "SELECT * FROM wash AS w " +
            "WHERE DATE(w.init_at) " +
            "BETWEEN :from AND :until",
            nativeQuery = true)
    List<Wash> getWashesByPeriod(@Param("from")LocalDate from,
                                @Param("until") LocalDate until);
}
