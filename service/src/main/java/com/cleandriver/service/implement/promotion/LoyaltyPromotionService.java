package com.cleandriver.service.implement.promotion;

import com.cleandriver.dto.promotion.LoyaltyPromotionDto;
import com.cleandriver.model.Appointment;
import com.cleandriver.model.promotions.AppointmentPromotion;
import com.cleandriver.model.promotions.LoyaltyPromotion;
import com.cleandriver.model.promotions.Promotion;
import com.cleandriver.persistence.PromotionRepository;
import com.cleandriver.service.interfaces.promotion.IAppointmentPromotionService;
import com.cleandriver.service.interfaces.promotion.IPromotionStrategy;
import com.cleandriver.service.interfaces.appointment.IAppointmentStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Service("LOYALTY")
public class LoyaltyPromotionService implements IPromotionStrategy {

    @Autowired
    private IAppointmentStatsService appointmentStatsService;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private IAppointmentPromotionService appointmentPromotionService;

    @Override
    public boolean isCompatible(Appointment appointment, Promotion promotion) {

        List<AppointmentPromotion> lastUses = appointmentPromotionService.getUseByPromotionAndPlateNumber(
                appointment.getVehicleToWash().getPlateNumber(),
                promotion.getId(),
                promotion.getStartDate(),
                promotion.getEndDate()
        );

        int usesAmount = lastUses.size();

        if(!promotion.isActive())
            return false;

        if(promotion.isOnlyCustomer() && appointment.getCustomer() == null)
            return false;

        if(!promotion.getServiceType().contains(appointment.getServiceType()))
            return false;

        if (promotion.getMaxUses() > usesAmount)
            return false;

        if(!promotion.getDaysToCollect().contains(appointment.getStartDateTime().toLocalDate().getDayOfWeek()))
            return false;

        if(!promotion.getDaysToReserve().contains(appointment.getStartDateTime().getDayOfWeek()))
            return false;

        return true;
    }



    @Override
    public BigDecimal applyDiscount(Appointment appointment,Promotion promotion) {

        BigDecimal initialPrice = appointment.getServiceType().getPrice();

        if (!hasRequiredAmountWash(promotion, appointment)) {
            return appointment.getServiceType().getPrice();
        }
        BigDecimal discountAmount = initialPrice
                .multiply(promotion.getDiscount())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        return initialPrice.subtract(discountAmount);
    }

    @Override
    public void createPromotion(Promotion promotion) {
        System.out.println("Clase real: " + promotion.getClass().getSimpleName());

        if(!(promotion instanceof LoyaltyPromotion))
            throw new RuntimeException("Error creand");

        if(((LoyaltyPromotion) promotion).getRequiredWash() < 0)
            throw new RuntimeException("La cantiadad de lavados requeridos no puede ser menor que cero");


    }

    @Override
    public void deletePromotion(Promotion promotion) {

    }



    private boolean hasRequiredAmountWash(Promotion promotion, Appointment appointment){
        LocalDateTime startDateFrom;

        List<AppointmentPromotion> lastUses = appointmentPromotionService.getUseByPromotionAndPlateNumber(
                appointment.getVehicleToWash().getPlateNumber(),
                promotion.getId(),
                promotion.getStartDate(),
                promotion.getEndDate())
                .stream()
                .filter(ap -> ap.getLastUse() != null)
                .toList();

        if (!lastUses.isEmpty()) {
            startDateFrom = lastUses.stream()
                    .max(Comparator.comparing(ap -> ap.getAppointment().getStartDateTime()))
                    .orElse(null)
                    .getLastUse();
        }else
            startDateFrom = promotion.getStartDate().atTime(LocalTime.MIDNIGHT);

        int washAmount = appointmentStatsService.getCompletedAppointmentsByPlateAndDateRangeAndService(
                appointment.getVehicleToWash().getPlateNumber(),
                ((LoyaltyPromotion) promotion).getTimeRangeInWeeks(),
                startDateFrom,
                promotion.getId(),
                appointment.getServiceType().getId()
        );

        if (washAmount < ((LoyaltyPromotion) promotion).getRequiredWash())
            return false;

        return true;

    }


}
