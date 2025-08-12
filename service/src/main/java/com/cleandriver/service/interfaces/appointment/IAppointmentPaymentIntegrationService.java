package com.cleandriver.service.interfaces.appointment;

import com.cleandriver.dto.appointment.AppointmentResponse;
import jakarta.transaction.Transactional;

import java.util.Map;

public interface IAppointmentPaymentIntegrationService {

    AppointmentResponse payCashAppointment(Long appointmentId);

//    AppointmentResponse onlyPayOnlineAppointment(Long appointmentId);

    @Transactional
    AppointmentResponse payOnlineAppointment(Long appointmentId);

    void handleMercadoPagoWebhook(Map<String, Object> response);
}
