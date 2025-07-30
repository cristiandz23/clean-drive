package com.cleandriver.model;

import com.cleandriver.model.enums.AppointmentStatus;
import jakarta.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Appointment {

    private Long id;

    private LocalDateTime dateTime;

    private AppointmentStatus status;

    private ServiceType serviceType;

    private Payment payment;

}
