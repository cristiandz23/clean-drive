package com.cleandriver.model;

import com.cleandriver.model.enums.AppointmentStatus;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long id;

    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "appointment_status")
    private AppointmentStatus status;

    @Column(nullable = false)
    private ServiceType serviceType;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_id")
    private Payment payment;

}
