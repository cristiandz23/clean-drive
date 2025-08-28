package com.cleandriver.service.implement.promotion;


import com.cleandriver.dto.promotion.ConsultPromotionsDto;
import com.cleandriver.dto.promotion.LoyaltyPromotionDto;
import com.cleandriver.dto.promotion.PromotionDto;
import com.cleandriver.dto.promotion.PromotionSummary;
import com.cleandriver.mapper.PromotionMapper;
import com.cleandriver.model.Appointment;
import com.cleandriver.model.ServiceType;
import com.cleandriver.model.promotions.AppointmentPromotion;
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
import java.time.LocalDate;
import java.util.ArrayList;
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

    private <T extends Promotion> T savePromotion(T promotion) {
        return (T) promotionRepository.save(promotion);
    }

    @Override
    public PromotionDto createPromotion(PromotionDto promotionDto) {;

        var promotion = promotionMapper.mapPolymorphic(promotionDto);

        List<ServiceType> serviceTypes = new ArrayList<>();
        promotionDto.getServiceType().forEach(
                st -> serviceTypes.add(serviceTypeService.getServiceType(st.id()))
        );

        String typeKey = promotion.getPromotionType().name();
        IPromotionStrategy strategy = strategyMap.get(typeKey);

        promotion.setCreatedAt(LocalDate.now());
        promotion.setServiceType(serviceTypes);

        strategy.createPromotion(promotion);

        Promotion pro = this.savePromotion(promotion);

        return promotionMapper.mapPolymorphic(pro);
    }

    @Override
    public PromotionDto findPromotionDto(Long promotionId) {
        return promotionMapper.mapPolymorphic(this.findPromotion(promotionId));
    }

    @Override
    public Promotion findPromotionById(Long promotionId) {
        return this.findPromotion(promotionId);
    }

    @Override
    public List<PromotionSummary> checkCompatibility(ConsultPromotionsDto promotionDto) {

        return this.findAllPromotions().stream()
                .filter( pr -> this.isCompatible(pr,promotionDto) )

                .filter(
                        promotion -> {
                            String typeKey = promotion.getPromotionType().name();
                            IPromotionStrategy strategy = strategyMap.get(typeKey);

                            return strategy.isCompatible(promotionDto, promotion);
                        }
                )
                .map(promotionMapper::toSummary)
                .toList();
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
                .map(promotionMapper::mapPolymorphic)
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

        isApplyPromotion(appointment,promotion);

        BigDecimal finalPrice = strategy.applyDiscount(appointment, promotion);

        if(finalPrice == appointment.getServiceType().getPrice()){
            appointmentPromotionService.saveUseWithoutDiscount(appointment,promotion);
        }else {
            appointmentPromotionService.saveUseWithDiscount(appointment,promotion);
        }

        return finalPrice;
    }

    private boolean isCompatible(Promotion promotion, ConsultPromotionsDto promotionsDto){

        if(promotionsDto.getPlateNumber() == null || promotionsDto.getVehicleType() == null
                || promotionsDto.getServiceType() == null || promotionsDto.getDateTime() == null)
            return false;


        List<AppointmentPromotion> lastUses = appointmentPromotionService.getUseByPromotionAndPlateNumber(
                        promotionsDto.getPlateNumber(),
                        promotion.getId(),
                        promotion.getStartDate(),
                        promotion.getEndDate()
                ).stream()
                .filter(ap -> ap.isWasApply())
                .toList();

        if(promotion.getMaxUses() < lastUses.size())
            return false;

        if(!promotion.isActive())
            return false;

        if(promotion.isOnlyCustomer() && promotionsDto.getCustomerDni() == null)
            return false;

        if(promotion.getServiceType()
                .stream()
                .noneMatch(st -> st.getId().equals(promotionsDto.getServiceType())))
            return false;

       if(promotion.getServiceType().stream()
                .noneMatch(st -> st.getVehicleType().contains(promotionsDto.getVehicleType())))
           return false;

        if(!promotion.getDaysToCollect().contains(promotionsDto.getDateTime().toLocalDate().getDayOfWeek()))
            return false;

        if(!promotion.getDaysToReserve().contains(LocalDate.now().getDayOfWeek()))
            return false;


        return true;
    }


    private void isApplyPromotion(Appointment appointment, Promotion promotion){

        if (!promotion.isActive())
            throw new RuntimeException("No se puede aplicar la promocion porque no esta activa");

        if (promotion.isOnlyCustomer() && appointment.getCustomer() == null)
            throw new RuntimeException("No se puede aplicar la promocion porque solo para clientes registrados");

        if (!promotion.getServiceType().contains(appointment.getServiceType()))
            throw new RuntimeException("Esta promocion no aplica para este tipo de servicio");

        if (!promotion.getDaysToCollect().contains(appointment.getStartDateTime().getDayOfWeek()))
            throw new RuntimeException("La promocion no aplica para este dia de la semana");

        if(getUses(promotion,appointment)>promotion.getMaxUses())
            throw new RuntimeException("Ya se uso el maximo de veces esta promocion");
    }

    private int getUses(Promotion promotion, Appointment appointment){
        return appointmentPromotionService.getUseByPromotionAndPlateNumber(
                appointment.getVehicleToWash().getPlateNumber(),
                promotion.getId(),
                promotion.getStartDate(),
                promotion.getEndDate()).stream().filter(
                        pr -> pr.getLastUse() != null)
                .toList().size();
    }

}
