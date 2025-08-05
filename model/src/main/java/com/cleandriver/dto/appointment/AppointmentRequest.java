package com.cleandriver.dto.appointment;

import com.cleandriver.model.enums.PaymentMethod;
import com.cleandriver.model.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppointmentRequest {


    private LocalDateTime dateTime;

    private Long serviceType;

    private PaymentMethod paymentMethod;

    private String customerDni;

    private Long washingStationId;

    private VehicleType vehicleType;

    private String plateNumber;



}

