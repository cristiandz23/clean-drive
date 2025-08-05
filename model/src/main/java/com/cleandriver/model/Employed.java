package com.cleandriver.model;

import com.cleandriver.model.importantesAhora.Area;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "Employed")
public class Employed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employed_id")
    private Long id;

    private String name;

    private String lastName;

    @Column(unique = true)
    private String dni;

    private String phone;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne
    private Area area;

    private LocalDateTime createdAt;

    private boolean isActive;

}
