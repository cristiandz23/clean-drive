package com.cleandriver.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data @Builder
@Entity(name = "wash")
public class Wash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wash_id")
    private Long id;

//    @OneToOne(optional = false)
//    @JoinColumn(name = "vehicle_id")
//    private Vehicle vehicle;

    private LocalDateTime initAt;

    private LocalDateTime endAt;

    @OneToOne(optional = false,cascade = {CascadeType.PERSIST})
    private Appointment appointment;

    @OneToOne
    @JoinColumn(name = "washing_station_id")
    private WashingStation washingStation;

    @OneToOne
    private Employed employed;

}
