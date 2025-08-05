package com.cleandriver.model;

import com.cleandriver.model.enums.AppointmentStatus;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data @Builder
@Entity(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long id;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "appointment_status")
    private AppointmentStatus status;

    @OneToOne(optional = false)
    @JoinColumn(name = "service_type_id")
    private ServiceType serviceType;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "washing_station_id", nullable = false)
    private WashingStation washingStation;

    @OneToOne
    private Vehicle vehicleToWash;

    private LocalDateTime createdAt;



}
