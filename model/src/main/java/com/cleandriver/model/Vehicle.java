package com.cleandriver.model;

import com.cleandriver.config.UppercaseConverter;
import com.cleandriver.model.enums.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data @Builder
@Entity(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Long id;

    @Column(unique = true, nullable = false)
    @Convert(converter = UppercaseConverter.class)
    private String plateNumber;

    private String brand;

    private String model;

    private String year;

    private String color;

    private String observation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "vehicle_type")
    private VehicleType vehicleType;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private LocalDate createdAt;

}
