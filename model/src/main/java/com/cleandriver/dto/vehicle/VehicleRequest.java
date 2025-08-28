package com.cleandriver.dto.vehicle;

import com.cleandriver.config.PlateNumberDeserializer;
import com.cleandriver.model.enums.VehicleType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VehicleRequest {

    @NotNull(message = "Plate number cannot be null")
    @Size(min = 7, max = 9, message = "PlateNumber should be between 7 and 9 characters")
    @JsonDeserialize(using = PlateNumberDeserializer.class)
    private String plateNumber;
    @NotEmpty
    private String brand;

    private String model;

    private String year;

    private String color;

    private String observation;

    @NotNull(message = "Must indicate a vehicle type")
    private VehicleType vehicleType;

//    private String customerDni;

}
