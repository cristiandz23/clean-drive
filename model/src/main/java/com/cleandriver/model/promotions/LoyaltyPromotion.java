package com.cleandriver.model.promotions;

import com.cleandriver.model.enums.PromotionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Data
@AllArgsConstructor
@Table(name = "loyalty_promotion")
public class LoyaltyPromotion extends Promotion{

    public LoyaltyPromotion() {
        super.setPromotionType(PromotionType.LOYALTY);
    }

    @Column(nullable = false)
    private int requiredWash;


    @Column(nullable = false)
    private int timeRangeInWeeks;

    @Override
    public PromotionType getPromotionType() {
        return PromotionType.LOYALTY;
    }
    @Override
    public void setPromotionType(PromotionType promotionType) {
    }

}


