package com.cleandriver.dto.appointment;


import com.cleandriver.model.enums.PaymentMethod;
import com.cleandriver.model.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor @AllArgsConstructor
@Data
public class ExpressAppointmentRequest {


    private LocalDateTime dateTime;

    private Long serviceType;

    private PaymentMethod payment;

    private String customerDni;

    private String plateNumber;

    private VehicleType vehicleType;

    private Long washingStation;

}
