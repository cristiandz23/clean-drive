package com.cleandriver.dto.appointment;

import com.cleandriver.model.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppointmentRequest {

    @NotNull(message = "dateTime cannot be null")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime dateTime;

    @NotNull(message = "serviceType cannot be null")
    @Positive
    private Long serviceType;

    @NotNull(message = "paymentMethod cannot be null")
    private PaymentMethod paymentMethod;

    @NotNull(message = "customerDni cannot be null")
    private String customerDni;

    private Long promotion;

    private Long washingStationId;

    @NotNull(message = "plateNumber cannot be null")
    @Size(message = "plateNumber size should be within 6 and 7 long ")
    private String plateNumber;



}

