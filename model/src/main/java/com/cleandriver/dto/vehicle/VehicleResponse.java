package com.cleandriver.dto.vehicle;

import com.cleandriver.dto.customer.CustomerSummary;
import com.cleandriver.model.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class VehicleResponse {


    private Long id;

    private String plateNumber;

    private String brand;

    private String model;

    private String year;

    private String color;

    private String observation;

    private VehicleType vehicleType;

    private CustomerSummary customer;

    private List<String> platesNumber;

}
