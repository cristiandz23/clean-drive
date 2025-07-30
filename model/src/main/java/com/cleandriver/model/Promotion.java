package com.cleandriver.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "promotion")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id")
    private Long id;

    private String tittle;

    private String description;

    private  double discountPercentage;

    private boolean active;

    @OneToMany(mappedBy = "promotion")
    private List<CustomerPromotion> customers;

}
