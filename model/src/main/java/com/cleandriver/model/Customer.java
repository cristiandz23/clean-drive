package com.cleandriver.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    private String phone;

    private String name;

    private LocalDateTime registeredAt;

    @OneToOne(cascade = {CascadeType.ALL},orphanRemoval = true)
    private Address address;

    @OneToMany(mappedBy = "vehicles",cascade = {CascadeType.PERSIST},orphanRemoval = true)
    private List<Vehicle> vehicles;


    @OneToMany(mappedBy = "customer", orphanRemoval = true)
    private List<CustomerPromotion> promotions;
}
