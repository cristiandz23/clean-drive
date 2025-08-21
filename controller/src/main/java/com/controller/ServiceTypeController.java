package com.controller;

import com.cleandriver.dto.serviceType.ServiceTypeDto;
import com.cleandriver.service.interfaces.IServiceTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/service-type")
public class ServiceTypeController {

    @Autowired
    private IServiceTypeService serviceTypeService;

    @PostMapping("create")
    public ResponseEntity<ServiceTypeDto> createAppointment(@RequestBody @Valid
                                                                 ServiceTypeDto serviceType){
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceTypeService
                .createServiceType(serviceType));
    }

    @GetMapping("get-services")
    public ResponseEntity<List<ServiceTypeDto>> getServices(){
        return ResponseEntity.status(HttpStatus.OK).body(serviceTypeService.getServices());
    }

    @DeleteMapping("delete-service/{id}")
    public ResponseEntity<String> deleteServiceType(@PathVariable Long id){
        serviceTypeService.deleteServiceType(id);
        return ResponseEntity.status(HttpStatus.OK).body("eliminado");
    }




}
