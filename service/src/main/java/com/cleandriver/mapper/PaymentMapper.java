package com.cleandriver.mapper;

import com.cleandriver.dto.payment.PaymentRequest;
import com.cleandriver.dto.payment.PaymentResponse;
import com.cleandriver.model.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    Payment toPayment(PaymentRequest paymentRequest);

    PaymentResponse toResponse(Payment payment);

}
