package com.cleandriver.dto.promotion;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromotionSummary {

    private Long id;

    private String tittle;

    private String description;

    private LocalDate endDate;

}
