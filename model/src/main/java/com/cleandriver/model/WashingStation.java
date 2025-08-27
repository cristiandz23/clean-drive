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

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "washingStation",fetch = FetchType.LAZY)
    private List<Appointment> appointments;

    private boolean isBusy;

    private boolean isAvailable;

    @Override
    public String toString() {
        return "WashingStation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isBusy=" + isBusy +
                '}';
    }
    //    private ServiceType serviceType;
}
