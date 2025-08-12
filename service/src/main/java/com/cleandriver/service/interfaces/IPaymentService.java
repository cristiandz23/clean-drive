package com.cleandriver.service.interfaces;

import com.cleandriver.dto.payment.PaymentResponse;
import com.cleandriver.model.Appointment;
import com.cleandriver.model.Payment;


public interface IPaymentService {


    PaymentResponse appointmentPayment(Appointment appointment);

    Payment savePayment(Payment payment);


}
