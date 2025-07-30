package com.cleandriver.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class CustomerPromotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_promotion_id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="promotion_id")
    private Promotion promotion;

    private LocalDateTime assignedAt;

    private boolean used;

    private LocalDateTime usedAt;

}
