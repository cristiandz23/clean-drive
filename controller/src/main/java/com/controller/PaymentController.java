package com.controller;


import com.cleandriver.service.interfaces.appointment.IAppointmentPaymentIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @Autowired
    private IAppointmentPaymentIntegrationService appointmentPaymentIntegrationService;

    @PostMapping("web")
    public ResponseEntity<String> createEmployed(@RequestBody
                                                           Map<String,Object> data){
        appointmentPaymentIntegrationService.handleMercadoPagoWebhook(data);
        return ResponseEntity.status(HttpStatus.OK).body("listo");
    }


}
