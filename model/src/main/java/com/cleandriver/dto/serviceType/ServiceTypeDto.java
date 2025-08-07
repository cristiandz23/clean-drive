package com.cleandriver.dto.serviceType;


import com.cleandriver.model.enums.VehicleType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceTypeDto {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal price;

    private String description;
    @NotNull
    private int durationInMinutes;

    @NotNull
    private List<VehicleType> vehicleType;


}
