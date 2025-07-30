package com.cleandriver.model;

import com.cleandriver.model.enums.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String plateNumber;

    private String model;

    private String color;

    private String observation;
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    private Customer customer;

}
