package com.controller;

import com.cleandriver.dto.serviceType.ServiceTypeDto;
import com.cleandriver.dto.vehicle.VehicleRequest;
import com.cleandriver.dto.vehicle.VehicleResponse;
import com.cleandriver.service.interfaces.IServiceTypeService;
import com.cleandriver.service.interfaces.IVehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {


    @Autowired
    private IVehicleService vehicleService;

    @GetMapping("/get-by-plate-number/{plateNumber}")
    public ResponseEntity<VehicleResponse> getVehicle(@PathVariable String plateNumber){
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService
                .findByPlateNumber(plateNumber));
    }

    @PutMapping("/update/{plateNumber}")
    public ResponseEntity<VehicleResponse> updateVehicle(@PathVariable String plateNumber,
                                                         @RequestBody VehicleRequest vehicle){

        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.updateVehicle(plateNumber,vehicle));
    }
}
