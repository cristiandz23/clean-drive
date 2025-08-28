package com.cleandriver.dto.promotion;


import com.cleandriver.config.DaysOfWeekConverter;
import com.cleandriver.dto.OnlyIdAndNameEntity;
import com.cleandriver.dto.serviceType.ServiceTypeSummary;
import com.cleandriver.model.enums.PromotionType;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.Convert;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "service")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LoyaltyPromotionDto.class, name = "LOYALTY"),
})
public class PromotionDto {

    private Long id;

    @NotNull
    private String tittle;

    private String description;

    @NotNull
    private boolean onlyCustomer;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDate;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDate;

    private boolean active;

    @NotNull
    private int maxUses;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @NotNull @Positive
    private double discount;

    @NotNull
    private boolean onlyOnce;

//    @NotNull
    private PromotionType promotionType;

    @NotNull
    private List<OnlyIdAndNameEntity> serviceType;

    @NotNull
    private List<DayOfWeek> daysToReserve;

    @NotNull
    private List<DayOfWeek> daysToCollect;


}
