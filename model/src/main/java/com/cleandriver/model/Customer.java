package com.cleandriver.model;

import com.cleandriver.model.promotions.Promotion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    private String phone;

    @Column(nullable = false)
    private String name;

    private String lastName;

    @Column(unique = true)
    private String dni;

    private LocalDate registeredAt;

    @OneToOne(cascade = {CascadeType.ALL},orphanRemoval = true)
    private Address address;

    @OneToMany(mappedBy = "customer",cascade = {CascadeType.PERSIST},orphanRemoval = true)
    private List<Vehicle> vehicles;


//    private Appointment appointment;

    @OneToMany( orphanRemoval = true)
    private List<Promotion> promotions;
}
