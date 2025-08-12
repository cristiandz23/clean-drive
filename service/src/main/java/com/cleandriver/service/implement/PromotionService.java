package com.cleandriver.service.implement;


import com.cleandriver.dto.promotion.LoyaltyPromotionRequest;
import com.cleandriver.dto.promotion.PromotionDto;
import com.cleandriver.exception.promotionException.PromotionInstanceIsNotSupportedException;
import com.cleandriver.mapper.PromotionMapper;
import com.cleandriver.model.Appointment;
import com.cleandriver.model.promotions.LoyaltyPromotion;
import com.cleandriver.model.promotions.Promotion;
import com.cleandriver.persistence.PromotionRepository;
import com.cleandriver.service.interfaces.appointment.IAppointmentStatsService;
import com.cleandriver.service.interfaces.IPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PromotionService implements IPromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private IAppointmentStatsService appointmentStatsService;

    @Autowired
    private PromotionMapper promotionMapper;


    private Promotion findPromotion(Long promotionId){
        return promotionRepository.findById(promotionId).orElseThrow(
                () -> new RuntimeException("No se encontro el codigo con el id: " + promotionId)
        );
    }

    @Override
    public PromotionDto createPromotion(LoyaltyPromotionRequest loyaltyPromotionRequest) {

        LoyaltyPromotion promotion = promotionMapper.toLoyaltyPromotion(loyaltyPromotionRequest);
        promotion.setCreatedAt(LocalDateTime.now());
        return promotionMapper.toPromotionDto(promotionRepository.save(promotion));
    }

    @Override
    public PromotionDto findPromotionDto(Long promotionId) {
        return promotionMapper.toPromotionDto(this.findPromotion(promotionId));
    }

    @Override
    public Promotion findPromotionById(Long promotionId) {
        return this.findPromotion(promotionId);
    }

    @Override
    public void deletePromotion(Long id) {

        Promotion promotion = findPromotion(id);

        promotionRepository.delete(promotion);
    }

    @Override
    public List<PromotionDto> findAllPromotionsDto() {
        return promotionRepository.findAll()
                .stream()
                .map(promotionMapper::toPromotionDto)
                .toList();
    }

    @Override
    public List<Promotion> findAllPromotions() {
        return promotionRepository.findAll();
    }

    @Override
    public PromotionDto updatePromotion(Long promotionId, LoyaltyPromotionRequest promotionRequest) {
        return null;
    }


    @Override
    public BigDecimal applyPromotion(Long promotionId, Appointment appointment){

        var promotion = this.findPromotion(promotionId);

        if(promotion.isOnlyCustomer() && appointment.getCustomer() == null){
            throw new RuntimeException("Pomocion solo para clientes registrados");
        }

        if (promotion instanceof LoyaltyPromotion) {

            int washAmount = appointmentStatsService.getWashAmountByDateAndPlantNumber(appointment.getVehicleToWash().getPlateNumber(),
                    ((LoyaltyPromotion) promotion).getTimeRangeInWeeks()); // modificar este metodo por lavados completados

            if (((LoyaltyPromotion) promotion).isApplicable(washAmount)) {

                BigDecimal price = appointment.getServiceType().getPrice();
                BigDecimal discount = BigDecimal.valueOf(((LoyaltyPromotion) promotion).getDiscount());
                price.multiply(BigDecimal.ONE.subtract(discount.divide(BigDecimal.valueOf(100))))
                        .setScale(2, RoundingMode.HALF_UP);
                return price;
            }
            return appointment.getServiceType().getPrice();

        }else if(promotion instanceof Promotion){

            BigDecimal price = appointment.getServiceType().getPrice();
            BigDecimal discount = BigDecimal.valueOf( promotion.getDiscount());

            price.multiply(BigDecimal.ONE.subtract(discount.divide(BigDecimal.valueOf(100))))
                    .setScale(2, RoundingMode.HALF_UP);
            return price;

        }

        throw new PromotionInstanceIsNotSupportedException("Tipo de promoción no soportado");
    }

    @Override
    public PromotionDto activatePromotion(Long promotionId, LocalDateTime promotionEnd) {

        Promotion promotion = findPromotion(promotionId);
        promotion.setStartDate(LocalDateTime.now());
        promotion.setEndDate(promotionEnd);

        return promotionMapper.toPromotionDto(promotionRepository.save(promotion));
    }

    @Override
    public PromotionDto deactivatePromotion(Long promotionId) {

        Promotion promotion = findPromotion(promotionId);
        promotion.setEndDate(LocalDateTime.now().minusDays(1));

        return promotionMapper.toPromotionDto(promotionRepository.save(promotion));
    }


}
