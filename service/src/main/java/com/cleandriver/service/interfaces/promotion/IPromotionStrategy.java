package com.cleandriver.service.interfaces.promotion;

import com.cleandriver.model.Appointment;
import com.cleandriver.model.promotions.Promotion;

import java.math.BigDecimal;

public interface IPromotionStrategy {

    boolean isCompatible(Appointment appointment, Promotion promotion);

    BigDecimal applyDiscount(Appointment appointment,Promotion promotion);

    void createPromotion(Promotion promotion);

    void deletePromotion(Promotion promotion);
}
