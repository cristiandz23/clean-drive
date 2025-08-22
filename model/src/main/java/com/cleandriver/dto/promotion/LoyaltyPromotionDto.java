package com.cleandriver.dto.promotion;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoyaltyPromotionDto extends PromotionDto {


    @Positive @Min(1) @NotNull
    private int requiredWash;

    @Positive @Min(1) @NotNull
    private int timeRangeInWeeks;

    private int missingWashes;


}
