package com.cleandriver.service.implement.promotion;


import com.cleandriver.dto.promotion.LoyaltyPromotionDto;
import com.cleandriver.dto.promotion.PromotionDto;
import com.cleandriver.mapper.PromotionMapper;
import com.cleandriver.model.Appointment;
import com.cleandriver.model.ServiceType;
import com.cleandriver.model.promotions.Promotion;
import com.cleandriver.persistence.PromotionRepository;
import com.cleandriver.service.interfaces.IServiceTypeService;
import com.cleandriver.service.interfaces.promotion.IAppointmentPromotionService;
import com.cleandriver.service.interfaces.promotion.IPromotionStrategy;
import com.cleandriver.service.interfaces.appointment.IAppointmentStatsService;
import com.cleandriver.service.interfaces.promotion.IPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class PromotionService implements IPromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private IAppointmentStatsService appointmentStatsService;

    @Autowired
    private PromotionMapper promotionMapper;

    @Autowired
    private Map<String, IPromotionStrategy> strategyMap;

    @Autowired
    private IServiceTypeService serviceTypeService;

    @Autowired
    private IAppointmentPromotionService appointmentPromotionService;


    private Promotion findPromotion(Long promotionId){
        return promotionRepository.findById(promotionId).orElseThrow(
                () -> new RuntimeException("No se encontro el codigo con el id: " + promotionId)
        );
    }

    @Override
    public PromotionDto createPromotion(PromotionDto promotionDto) {;

        Promotion promotion = promotionMapper.toEntity(promotionDto);

        List<ServiceType> serviceTypes = new ArrayList<>();
        promotionDto.getServiceType().forEach(
                st -> serviceTypes.add(serviceTypeService.getServiceType(st))
        );

        String typeKey = promotion.getPromotionType().name();
        IPromotionStrategy strategy = strategyMap.get(typeKey);

        promotion.setCreatedAt(LocalDateTime.now());
        promotion.setServiceType(serviceTypes);

        strategy.createPromotion(promotion);

        return promotionMapper.toDto(promotionRepository.save(promotion));
    }

    @Override
    public PromotionDto findPromotionDto(Long promotionId) {
        return promotionMapper.toDto(this.findPromotion(promotionId));
    }

    @Override
    public Promotion findPromotionById(Long promotionId) {
        return this.findPromotion(promotionId);
    }

    @Override
    public void deletePromotion(Long id) {
        Promotion promotion = this.findPromotion(id);

        String typeKey = promotion.getPromotionType().name();
        IPromotionStrategy strategy = strategyMap.get(typeKey);

        if(appointmentPromotionService.havePendingAppointment(id))
            throw new RuntimeException("Hay turnos vinculados a esta promocion que ya han sido pagados");

        strategy.deletePromotion(promotion);

        promotionRepository.delete(promotion);
    }

    @Override
    public List<PromotionDto> findAllPromotionsDto() {
        return promotionRepository.findAll()
                .stream()
                .map(promotionMapper::toDto)
                .toList();
    }

    @Override
    public List<Promotion> findAllPromotions() {
        return promotionRepository.findAll();
    }

    @Override
    public PromotionDto updatePromotion(Long promotionId, LoyaltyPromotionDto promotionRequest) {
        return null;
    }



    @Override
    public BigDecimal applyPromotion(Long promotionId, Appointment appointment){

        Promotion promotion = this.findPromotion(promotionId);

        String typeKey = promotion.getPromotionType().name();
        IPromotionStrategy strategy = strategyMap.get(typeKey);

        BigDecimal finalPrice = strategy.applyDiscount(appointment, promotion);

        appointmentPromotionService.saveUse(appointment,promotion);

        return finalPrice;
    }

}
