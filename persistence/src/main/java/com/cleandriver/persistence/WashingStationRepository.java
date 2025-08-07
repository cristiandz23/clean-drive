package com.cleandriver.persistence;

import com.cleandriver.model.WashingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WashingStationRepository extends JpaRepository<WashingStation,Long> {


//    @Query("SELECT ws FROM WashingStation ws " +
//            "WHERE ws.id NOT IN (" +
//            "SELECT a.washingStation.id FROM Appointment a " +
//            "WHERE a.startDateTime < :end AND a.endDateTime > :start)")
//    List<WashingStation> findAvailableStations(@Param("start") LocalDateTime start,
//                                               @Param("end") LocalDateTime end);

    @Query(value = "SELECT * FROM washing_station AS ws " +
            "WHERE ws.washing_station_id NOT IN (" +
            "SELECT a.washing_station_id FROM appointment AS a " +
            "WHERE a.start_date_time < :end AND a.end_date_time > :start)",nativeQuery = true)
    List<WashingStation> findAvailableStations(@Param("start") LocalDateTime start,
                                               @Param("end") LocalDateTime end);



}
