package com.cleandriver.dto.appointment;

import com.cleandriver.dto.customer.CustomerResponse;
import com.cleandriver.dto.payment.PaymentResponse;
import com.cleandriver.dto.serviceType.ServiceTypeDto;
import com.cleandriver.model.WashingStation;
import com.cleandriver.model.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppointmentResponse {

    private Long id;

    private AppointmentStatus appointmentStatus;

    private ServiceTypeDto ServiceType;

    private PaymentResponse payment;

    private CustomerResponse customer;

    private String stationName;

}
