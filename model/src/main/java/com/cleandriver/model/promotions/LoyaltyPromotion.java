package com.cleandriver.model.promotions;

import com.cleandriver.model.Appointment;
import com.cleandriver.model.enums.PromotionType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@DiscriminatorValue("LOYALTY")
@Data
@AllArgsConstructor
public class LoyaltyPromotion extends Promotion{

    public LoyaltyPromotion() {
        super.setPromotionType(PromotionType.LOYALTY);
    }

    @Column(nullable = false)
    private int requiredWash;


    @Column(nullable = false)
    private int timeRangeInWeeks;



}


