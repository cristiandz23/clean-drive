package com.controller;


import com.cleandriver.dto.promotion.PromotionDto;
import com.cleandriver.service.interfaces.promotion.IPromotionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/promotion")
public class PromotionController {

    @Autowired
    private IPromotionService promotionService;



    @PostMapping("create")
    public ResponseEntity<PromotionDto> createPromotion(@RequestBody @Valid
                                                               PromotionDto promotion){
        System.out.println("controller Clase real: " + promotion.getClass().getSimpleName());

        return ResponseEntity.status(HttpStatus.CREATED).body(promotionService
                .createPromotion(promotion));
    }

    @DeleteMapping("delete/{promotionId}")
    public ResponseEntity<String> createPromotion(@PathVariable Long promotionId){
        promotionService.deletePromotion(promotionId);
        return ResponseEntity.status(HttpStatus.OK).body("Eliminado");
    }

    @GetMapping("/get-by-id/{promotionId}")
    public ResponseEntity<PromotionDto> getPromotion(@RequestBody @Valid
                                                        Long promotionId){
        return ResponseEntity.status(HttpStatus.OK).body(promotionService.findPromotionDto(promotionId));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<PromotionDto>> getAllPromotion(){
        return ResponseEntity.status(HttpStatus.OK).body(promotionService.findAllPromotionsDto());
    }

    @GetMapping
    public ResponseEntity<PromotionDto> getAvailablePromotion()


}
