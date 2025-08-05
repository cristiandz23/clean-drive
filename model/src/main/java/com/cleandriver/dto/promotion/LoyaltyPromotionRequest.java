package com.cleandriver.dto.promotion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoyaltyPromotionRequest {

    private String tittle;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int requiredWash;

    private int time;

    private double discount;

}
