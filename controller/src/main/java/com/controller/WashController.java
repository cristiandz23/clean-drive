package com.controller;


import com.cleandriver.dto.serviceType.ServiceTypeDto;
import com.cleandriver.dto.wash.WashRequest;
import com.cleandriver.dto.wash.WashResponse;
import com.cleandriver.service.interfaces.IServiceTypeService;
import com.cleandriver.service.interfaces.IWashService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wash")
public class WashController {

    @Autowired
    private IWashService washService;

    @PostMapping("init")
    public ResponseEntity<WashResponse> initWash(@RequestBody @Valid
                                                 WashRequest wash){
        return ResponseEntity.status(HttpStatus.CREATED).body(washService.initWash(wash));
    }
}
