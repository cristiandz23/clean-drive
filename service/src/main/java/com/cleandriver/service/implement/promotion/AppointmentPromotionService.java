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

    private AppointmentPromotion getAppointmentPromotion(Long id){
        return appointmentPromotionRepository.findById(id).orElseThrow(
                () -> new RuntimeException("no se econtro appointment promotion")
        );
    }


    @Override
    public AppointmentPromotion saveUseWithDiscount(Appointment appointment, Promotion promotion) {

        AppointmentPromotion use = AppointmentPromotion.builder()
                .appointment(appointment)
                .lastUse(LocalDate.now())
                .promotion(promotion)
                .build();
        return appointmentPromotionRepository.save(use);
    }

    @Override
    public AppointmentPromotion saveUseWithoutDiscount(Appointment appointment, Promotion promotion) {
        AppointmentPromotion use = AppointmentPromotion.builder()
                .appointment(appointment)
                .lastUse(null)
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
