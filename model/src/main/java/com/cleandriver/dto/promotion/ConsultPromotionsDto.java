package com.cleandriver.dto.promotion;


import com.cleandriver.config.PlateNumberDeserializer;
import com.cleandriver.model.enums.PaymentMethod;
import com.cleandriver.model.enums.VehicleType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConsultPromotionsDto {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateTime;

    private Long serviceType;

    private PaymentMethod paymentMethod;

    private String customerDni;

    @JsonDeserialize(using = PlateNumberDeserializer.class)
    private String plateNumber;

    private VehicleType vehicleType;


}
