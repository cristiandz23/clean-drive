package com.cleandriver.persistence;

import com.cleandriver.model.promotions.AppointmentPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentPromotionRepository extends JpaRepository<AppointmentPromotion,Long> {


    @Query(value = "SELECT app.* FROM appointment_promotion AS app " +
            "JOIN appointment AS ap ON app.appointment_id = ap.appointment_id " +
            "JOIN vehicle AS v ON v.vehicle_id = ap.vehicle_id " +
            "WHERE app.promotion_id = :promotionId " +
            "AND v.plate_number = :plateNumber " +
//            "AND app.was_apply = true" +
            "AND ap.appointment_status = 'COMPLETED' " +
            "AND ap.start_date_time BETWEEN :startPromotion AND :endPromotion " +
            "ORDER BY ap.start_date_time DESC ",
            nativeQuery = true

    )
    List<AppointmentPromotion> findUsesByPromotionAndPlateNumber(@Param("plateNumber") String plateNumber,
                                                                 @Param("promotionId") Long promotionId,
                                                                 @Param("startPromotion") LocalDate startPromotion,
                                                                 @Param("endPromotion") LocalDate endPromotion);

    @Query(
            value = "SELECT COUNT(ap)>0 " +
                    "FROM AppointmentPromotion app " +
                    "JOIN app.appointment ap " +
                    "WHERE ap.status IN ('CONFIRMED','NO_SHOW','IN_PROGRESS') " +
                    "AND app.promotion.id  = :promotionId"
    )
    boolean havePendingAppointments(@Param("promotionId") Long promotionId);

//    @Query(
//            value = "SELECT COUNT(*)>0 " +
//                    "FROM appointment_promotion AS app " +
//                    "JOIN appointment AS ap ON app.appointment_id = app.appointment_id " +
//                    "WHERE ap.appointment_status IN ('CONFIRMED','NO_SHOW','IN_PROGRESS') " +
//                    "AND app.promotion_id = :promotionId",
//            nativeQuery = true
//    )
//    boolean havePendingAppointments(@Param("promotionId") Long promotionId);
}
