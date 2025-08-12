package com.cleandriver.dto.payment;

import com.cleandriver.model.enums.PaymentMethod;
import com.cleandriver.model.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentResponse {

    private Long id;

    private Long walletPaymentId;

    private PaymentStatus paymentStatus;

    private PaymentMethod paymentMethod;

    private BigDecimal amount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime paymentDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    private String url;


}
