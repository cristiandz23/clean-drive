package com.cleandriver.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "wash")
public class Wash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wash_id")
    private Long id;

    @Column(nullable = false)
    private Vehicle vehicle;

    @Column(nullable = false)
    private Appointment appointment;

    @OneToOne()
    @JoinColumn(name = "washing_station_id")
    private WashingStation washingStation;

    @Column(nullable = false)
    private Employed employed;

}
