package com.controller;


import com.cleandriver.dto.appointment.AppointmentRequest;
import com.cleandriver.dto.appointment.AppointmentResponse;
import com.cleandriver.dto.appointment.ExpressAppointmentRequest;
import com.cleandriver.service.interfaces.IAppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {


    @Autowired
    private IAppointmentService appointmentService;

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

}
