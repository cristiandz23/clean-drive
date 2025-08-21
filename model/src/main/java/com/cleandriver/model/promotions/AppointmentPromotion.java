package com.cleandriver.model.promotions;

import com.cleandriver.model.Appointment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = ("appointment_promotion"))
public class AppointmentPromotion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    private LocalDateTime lastUse;

}
