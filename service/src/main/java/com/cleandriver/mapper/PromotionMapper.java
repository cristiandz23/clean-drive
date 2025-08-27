package com.cleandriver.mapper;

import com.cleandriver.dto.promotion.LoyaltyPromotionDto;
import com.cleandriver.dto.promotion.PromotionDto;
import com.cleandriver.model.ServiceType;
import com.cleandriver.model.promotions.LoyaltyPromotion;
import com.cleandriver.model.promotions.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring",uses = ServiceTypeMapper.class)
public interface PromotionMapper {

    PromotionDto toDto(Promotion promotion);

    LoyaltyPromotionDto toDto(LoyaltyPromotion promo);


    @Mapping(target = "serviceType", ignore = true)
    LoyaltyPromotion toEntity(LoyaltyPromotionDto dto);


    @Named("mapServiceTypes")
    default List<String> mapPlateNumbers(List<ServiceType> service) {
        if (service == null) return List.of();
        return service.stream()
                .map(ServiceType::getName)
                .toList();
    }

    default Promotion mapPolymorphic(PromotionDto dto){
        System.out.println("0 maper real: " + dto.getClass().getSimpleName());

        if(dto instanceof LoyaltyPromotionDto){
            System.out.println("1maper real: " + dto.getClass().getSimpleName());

            return this.toEntity((LoyaltyPromotionDto) dto);
        }
        System.out.println("2 maper Clase real: " + dto.getClass().getSimpleName());
        throw new RuntimeException("Error al mapear");
//        return toEntity(dto);
    }

    default PromotionDto mapPolymorphic(Promotion entity){
        if (entity instanceof LoyaltyPromotion){
            return toDto((LoyaltyPromotion) entity);
        }
//        return toDto(entity);
        throw new RuntimeException("Error al mapear");

    }

}
