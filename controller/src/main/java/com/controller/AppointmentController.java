package com.controller;


import com.cleandriver.dto.appointment.AppointmentRequest;
import com.cleandriver.dto.appointment.AppointmentResponse;
import com.cleandriver.dto.appointment.ExpressAppointmentRequest;
import com.cleandriver.service.interfaces.appointment.IAppointmentPaymentIntegrationService;
import com.cleandriver.service.interfaces.appointment.IAppointmentService;
import com.cleandriver.service.interfaces.appointment.IAppointmentStatsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {


    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private IAppointmentPaymentIntegrationService appointmentPaymentIntegrationService;
    @Autowired
    private IAppointmentStatsService appointmentStatsService;


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

    @GetMapping("get-appointment-today")
    public ResponseEntity<List<AppointmentResponse>> appointmentToday(){

        return ResponseEntity.status(HttpStatus.OK).body(appointmentStatsService.findTodayAppointments());
    }

    @GetMapping("get-appointment-by-day")
    public ResponseEntity<List<AppointmentResponse>> appointmentsByDay(@RequestParam LocalDate date){

        return ResponseEntity.status(HttpStatus.OK).body(appointmentStatsService.getAppointmentsByDate(date));
    }

    @GetMapping("get-appointment-by-customer")
    public ResponseEntity<List<AppointmentResponse>> appointmentsByCustomer(@RequestParam String dni){

        return ResponseEntity.status(HttpStatus.OK).body(appointmentStatsService.getAppointmentByCustomer(dni));
    }

    @GetMapping("get-appointment-by-plateNumber")
    public ResponseEntity<List<AppointmentResponse>> appointmentsByPlateNumber(@RequestParam String plateNumber){

        return ResponseEntity.status(HttpStatus.OK).body(appointmentStatsService.getAppointmentByPlateNumber(plateNumber));
    }

    //convertir los parametros plate number a mayusculas y quitar espacion
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, "plateNumber", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(text != null ? text.replaceAll("\\s+", "").toUpperCase() : null);
            }
        });
    }


}
