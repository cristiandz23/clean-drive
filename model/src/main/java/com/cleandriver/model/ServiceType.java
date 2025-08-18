package com.cleandriver.model;

import com.cleandriver.config.VehicleTypeListConverter;
import com.cleandriver.model.enums.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "Service_Type")
public class ServiceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_type_id")
    private Long id;

    @Column(unique = true,nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int durationInMinutes;

    private String description;

    @Convert(converter = VehicleTypeListConverter.class)
    @Column(name = "vehicle_types")
    private List<VehicleType> vehicleType;

    private LocalDate createdAt;



}

