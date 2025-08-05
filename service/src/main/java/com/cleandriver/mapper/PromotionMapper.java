package com.cleandriver.mapper;

import com.cleandriver.dto.promotion.LoyaltyPromotionRequest;
import com.cleandriver.dto.promotion.PromotionDto;
import com.cleandriver.model.promotions.LoyaltyPromotion;
import com.cleandriver.model.promotions.Promotion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PromotionMapper {

    Promotion toPromotion(PromotionDto promotionDto);

    LoyaltyPromotion toLoyaltyPromotion(LoyaltyPromotionRequest loyaltyPromotionRequest);

    PromotionDto toPromotionDto(Promotion promotion);

    PromotionDto toPromotionDto(LoyaltyPromotion loyaltyPromotion);



}
