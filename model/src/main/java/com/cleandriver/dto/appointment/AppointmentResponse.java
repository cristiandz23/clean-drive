package com.cleandriver.dto.appointment;

import com.cleandriver.dto.customer.CustomerResponse;
import com.cleandriver.dto.customer.CustomerSummary;
import com.cleandriver.dto.payment.PaymentResponse;
import com.cleandriver.dto.serviceType.ServiceTypeDto;
import com.cleandriver.dto.serviceType.ServiceTypeSummary;
import com.cleandriver.dto.vehicle.VehicleSummary;
import com.cleandriver.model.Vehicle;
import com.cleandriver.model.WashingStation;
import com.cleandriver.model.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppointmentResponse {

    private Long id;

    private AppointmentStatus status;

    private ServiceTypeSummary ServiceType;

    private PaymentResponse payment;

    private CustomerSummary customer;

    private VehicleSummary vehicleToWash;

    private String stationName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDateTime;

}
