package com.cleandriver.dto.promotion;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoyaltyPromotionDto extends PromotionDto {


    @NotNull
    private int requiredWash;

    @NotNull
    private int timeRangeInWeeks;


}
