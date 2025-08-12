package com.controller;


import com.cleandriver.dto.appointment.AppointmentRequest;
import com.cleandriver.dto.appointment.AppointmentResponse;
import com.cleandriver.dto.appointment.ExpressAppointmentRequest;
import com.cleandriver.service.interfaces.appointment.IAppointmentPaymentIntegrationService;
import com.cleandriver.service.interfaces.appointment.IAppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {


    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private IAppointmentPaymentIntegrationService appointmentPaymentIntegrationService;


    @PostMapping("create")
    public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody @Valid
                                                                 AppointmentRequest appointment){
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService
                .createAppointment(appointment));
    }

    @PostMapping("/create-express")
    public ResponseEntity<AppointmentResponse> createExpressAppointment(@RequestBody @Valid
                                                                        ExpressAppointmentRequest appointment){
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService
                .createExpressAppointment(appointment));
    }

    @PostMapping("/pay-online/{appointmentId}")
    public ResponseEntity<AppointmentResponse> payAppointmentOnline(@PathVariable Long appointmentId){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                appointmentPaymentIntegrationService.payOnlineAppointment(appointmentId));
    }

    @PostMapping("/pay-cash/{appointmentId}")
    public ResponseEntity<AppointmentResponse> payAppointmentInCash(@PathVariable Long appointmentId){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                appointmentPaymentIntegrationService.payCashAppointment(appointmentId));
    }

}
