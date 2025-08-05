package com.cleandriver.service.interfaces;


import com.cleandriver.dto.promotion.LoyaltyPromotionRequest;
import com.cleandriver.dto.promotion.PromotionDto;
import com.cleandriver.model.Appointment;
import com.cleandriver.model.promotions.Promotion;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IPromotionService {

    PromotionDto createPromotion(LoyaltyPromotionRequest promotion);

    PromotionDto findPromotionDto(Long promotionId);

    Promotion findPromotionById(Long promotionId);

    void deletePromotion(Long id);

    List<PromotionDto> findAllPromotionsDto();

    List<Promotion> findAllPromotions();

    PromotionDto updatePromotion(Long promotionId, LoyaltyPromotionRequest promotionRequest);

    BigDecimal applyPromotion(Long promotionId, Appointment appointment);

    PromotionDto activatePromotion(Long promotionId, LocalDateTime promotionEnd);

    PromotionDto deactivatePromotion(Long promotionId);





}
