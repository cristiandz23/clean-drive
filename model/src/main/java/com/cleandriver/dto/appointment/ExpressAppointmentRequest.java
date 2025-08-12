package com.cleandriver.dto.appointment;


import com.cleandriver.dto.vehicle.VehicleRequest;
import com.cleandriver.model.enums.PaymentMethod;
import com.cleandriver.model.enums.VehicleType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor @AllArgsConstructor
@Data
public class ExpressAppointmentRequest {


    @NotNull(message = "dateTime cannot be null")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime dateTime;

    @NotNull(message = "serviceType cannot be null")
    @Positive
    private Long serviceType;

    @NotNull(message = "paymentMethod cannot be null")
    private PaymentMethod payment;

    @NotNull
    private VehicleRequest vehicle;

    private String customerDni;


    private Long promotion;

    @Positive
    private Long washingStation;

}
