package com.cleandriver.service.interfaces.promotion;


import com.cleandriver.dto.promotion.ConsultPromotionsDto;
import com.cleandriver.dto.promotion.LoyaltyPromotionDto;
import com.cleandriver.dto.promotion.PromotionDto;
import com.cleandriver.dto.promotion.PromotionSummary;
import com.cleandriver.model.Appointment;
import com.cleandriver.model.promotions.Promotion;

import java.math.BigDecimal;
import java.util.List;

public interface IPromotionService {

    PromotionDto createPromotion(PromotionDto promotion);

    PromotionDto findPromotionDto(Long promotionId);

    Promotion findPromotionById(Long promotionId);

    List<PromotionSummary> checkCompatibility(ConsultPromotionsDto promotionDto);

    void deletePromotion(Long id);

    List<PromotionDto> findAllPromotionsDto();

    List<Promotion> findAllPromotions();

    PromotionDto updatePromotion(Long promotionId, LoyaltyPromotionDto promotionRequest);

    BigDecimal applyPromotion(Long promotionId, Appointment appointment);







}
