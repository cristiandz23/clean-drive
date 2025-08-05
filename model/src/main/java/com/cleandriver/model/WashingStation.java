package com.cleandriver.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "washing_station")
public class WashingStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "washing_station_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "washingStation")
    private List<Appointment> appointments;

    private boolean isBusy;

//    private ServiceType serviceType;
}
