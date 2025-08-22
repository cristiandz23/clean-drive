package com.cleandriver.service.interfaces.promotion;

import com.cleandriver.model.Appointment;
import com.cleandriver.model.promotions.AppointmentPromotion;
import com.cleandriver.model.promotions.Promotion;

import java.time.LocalDate;
import java.util.List;

public interface IAppointmentPromotionService {

    AppointmentPromotion saveUseWithDiscount(Appointment appointment, Promotion promotion);
    AppointmentPromotion saveUseWithoutDiscount(Appointment appointment, Promotion promotion);
    List<AppointmentPromotion> getUseByPromotionAndPlateNumber(String plateNumber, Long id, LocalDate startPromotion, LocalDate endPromotion);

    boolean havePendingAppointment (Long promotionId);

}
