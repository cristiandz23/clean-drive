package com.cleandriver.mapper;

import com.cleandriver.dto.promotion.LoyaltyPromotionDto;
import com.cleandriver.dto.promotion.PromotionDto;
import com.cleandriver.model.ServiceType;
import com.cleandriver.model.Vehicle;
import com.cleandriver.model.promotions.LoyaltyPromotion;
import com.cleandriver.model.promotions.Promotion;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.security.Provider;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PromotionMapper {

    // Mapping polimórfico de Promocion a DTO
    @Mapping(target = "serviceType", source = "serviceType", qualifiedByName = "mapServiceTypes")
    PromotionDto toDto(Promotion promotion);

    // Mapeos inversos específicos
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "serviceType", ignore = true)
    @Mapping(target = "serviceType", source = "serviceType", qualifiedByName = "mapServiceTypes")
    PromotionDto toDto(LoyaltyPromotion promo);

    //    // Opcional: mapeo inverso DTO -> Entidad
//    @InheritInverseConfiguration
    @Mapping(target = "serviceType", ignore = true)
    Promotion toEntity(PromotionDto dto);

    //    @InheritInverseConfiguration
    @Mapping(target = "serviceType", ignore = true)
    LoyaltyPromotion toEntity(LoyaltyPromotionDto dto);


    @Named("mapServiceTypes")
    default List<Long> mapPlateNumbers(List<ServiceType> service) {
        if (service == null) return List.of();
        return service.stream()
                .map(ServiceType::getId)
                .toList();
    }

}
