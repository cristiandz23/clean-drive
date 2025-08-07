package com.cleandriver.dto.vehicle;

import com.cleandriver.model.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Data
public class VehicleSummary {

    private Long id;
    private String plateNumber;
    private VehicleType vehicleType;


}
