package com.cleandriver.model;

import com.cleandriver.model.enums.AppointmentStatus;
import com.cleandriver.model.promotions.AppointmentPromotion;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


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

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_type_id")
    private ServiceType serviceType;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "washing_station_id")//, nullable = false
    private WashingStation washingStation;

    @ManyToOne(optional = false,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicleToWash;

    private LocalDateTime createdAt;

    @PrePersist
    @PreUpdate
    public void truncateDate() {
        if (createdAt != null) {
            createdAt = createdAt.truncatedTo(ChronoUnit.MINUTES);
        }
        if (startDateTime != null) {
            startDateTime = startDateTime.truncatedTo(ChronoUnit.MINUTES);
        }
        if (endDateTime != null) {
            endDateTime = endDateTime.truncatedTo(ChronoUnit.MINUTES);
        }


    }
}
