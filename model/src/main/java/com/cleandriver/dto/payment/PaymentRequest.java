package com.cleandriver.dto.payment;

import com.cleandriver.model.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentRequest {

    private Long id;

    private PaymentMethod paymentMethod;

}
