package com.cleandriver.model;

import com.cleandriver.model.enums.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Long id;

    @Column(unique = true,nullable = false)
    private String plateNumber;

    private String model;

    private String color;

    private String observation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "vehicle_type")
    private VehicleType vehicleType;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @Column(nullable = false)
    private Customer customer;

}
