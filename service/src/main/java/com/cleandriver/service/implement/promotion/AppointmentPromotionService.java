package com.cleandriver.service.implement.promotion;

import com.cleandriver.model.Appointment;
import com.cleandriver.model.promotions.AppointmentPromotion;
import com.cleandriver.model.promotions.Promotion;
import com.cleandriver.persistence.AppointmentPromotionRepository;
import com.cleandriver.service.interfaces.promotion.IAppointmentPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentPromotionService implements IAppointmentPromotionService {


    @Autowired
    private AppointmentPromotionRepository appointmentPromotionRepository;

    @Override
    public AppointmentPromotion saveUse(Appointment appointment, Promotion promotion) {

        AppointmentPromotion use = AppointmentPromotion.builder()
                .appointment(appointment)
                .lastUse(LocalDateTime.now())
                .promotion(promotion)
                .build();
        return appointmentPromotionRepository.save(use);
    }


    @Override
    public List<AppointmentPromotion> getUseByPromotionAndPlateNumber(String plateNumber, Long promotionId,
                                                                LocalDate startPromotion, LocalDate endPromotion) {

        return appointmentPromotionRepository
                .findUsesByPromotionAndPlateNumber(plateNumber,promotionId,startPromotion,endPromotion);
    }

    @Override
    public boolean havePendingAppointment(Long promotionId) {
        return appointmentPromotionRepository.havePendingAppointments(promotionId);
    }
}
