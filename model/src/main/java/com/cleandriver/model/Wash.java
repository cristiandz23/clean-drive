package com.cleandriver.model;


import com.cleandriver.model.enums.WashStatus;
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
@Entity(name = "wash")
public class Wash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wash_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private WashStatus status;

    private LocalDateTime initAt;

    private LocalDateTime endAt;

    @OneToOne(optional = false,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "washing_station_id")
    private WashingStation washingStation;

    @ManyToOne
    @JoinColumn(name = "employed_id")
    private Employed employed;

    @PrePersist
    @PreUpdate
    public void truncateDate() {
        if (initAt != null) {
            initAt = initAt.truncatedTo(ChronoUnit.MINUTES);
        }
        if (endAt != null) {
            endAt = endAt.truncatedTo(ChronoUnit.MINUTES);
        }

    }

}
