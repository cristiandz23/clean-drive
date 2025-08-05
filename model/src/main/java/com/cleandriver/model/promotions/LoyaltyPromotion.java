package com.cleandriver.model.promotions;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@DiscriminatorValue("LOYALTY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoyaltyPromotion extends Promotion{

    @Column(nullable = false)
    private int requiredWash;

    @Column(nullable = false)
    private int timeRangeInWeeks;

    @Column(nullable = false)
    private double discount;

    public boolean isApplicable(int washAmount){

        if(!this.isActive())
            throw new RuntimeException("La Promotion no esta activa en este momento");

        return washAmount >= requiredWash;

    }

}
