package com.cleandriver.model;

import com.cleandriver.model.enums.PaymentMethod;
import com.cleandriver.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@NoArgsConstructor
@AllArgsConstructor
@Data @Builder
@Entity(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private BigDecimal amount;

    private LocalDateTime paymentDate;

    private LocalDateTime createdAt;

    private Long walletPaymentId;


    @PrePersist
    @PreUpdate
    public void truncateDate() {
        if (createdAt != null) {
            createdAt = createdAt.truncatedTo(ChronoUnit.MINUTES);
        }
        if (paymentDate != null) {
            paymentDate = paymentDate.truncatedTo(ChronoUnit.MINUTES);
        }

    }


}
