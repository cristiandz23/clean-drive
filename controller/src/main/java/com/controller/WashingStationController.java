package com.controller;

import com.cleandriver.dto.washingstation.WashingStationDto;
import com.cleandriver.service.interfaces.IWashingStationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/washing-station")
public class WashingStationController {


    @Autowired
    private IWashingStationService serviceTypeService;

    @PostMapping("create")
    public ResponseEntity<WashingStationDto> createWashingStation(@RequestBody @Valid
                                                               WashingStationDto serviceType){
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceTypeService
                .createWashingStation(serviceType));
    }

    @DeleteMapping("delete/{stationId}")
    public ResponseEntity<String> deleteWashingStation(@PathVariable Long stationId){
        serviceTypeService.deleteWashingStation(stationId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Eliminado");
    }


}
