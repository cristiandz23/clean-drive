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


    @Query("""
    SELECT DISTINCT ws 
    FROM WashingStation ws
    JOIN ws.appointments a
    WHERE (:start < a.dateTimeEnd AND :end > a.dateTimeStart)
""")
    List<WashingStation> findOccupiedStationsBetween(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

}
