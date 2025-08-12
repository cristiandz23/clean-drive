package com.cleandriver.service.implement.appointment;


import com.cleandriver.dto.appointment.AppointmentResponse;
import com.cleandriver.dto.payment.PaymentResponse;
import com.cleandriver.exception.paymentException.PaymentProcessingException;
import com.cleandriver.mapper.AppointmentMapper;
import com.cleandriver.model.Appointment;
import com.cleandriver.model.Payment;
import com.cleandriver.model.enums.AppointmentStatus;
import com.cleandriver.model.enums.PaymentMethod;
import com.cleandriver.model.enums.PaymentStatus;
import com.cleandriver.service.interfaces.appointment.IAppointmentPaymentIntegrationService;
import com.cleandriver.service.interfaces.appointment.IAppointmentService;
import com.cleandriver.service.interfaces.IPaymentService;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

import static com.cleandriver.configuration.ApplicationEnvironment.MP_ACCESS_TOKEN;

@Service
public class AppointmentPaymentIntegrationService implements IAppointmentPaymentIntegrationService {

    @Autowired
    private IAppointmentService appointmentService;
    @Autowired
    private IPaymentService paymentService;
    @Autowired
    private AppointmentMapper appointmentMapper;



    @Override
    @Transactional
    public AppointmentResponse payCashAppointment(Long appointmentId){
        AppointmentResponse appointment = appointmentService.confirmAndSelectWashingStation(appointmentId);

        if(appointment.getPayment().getPaymentMethod() == PaymentMethod.VIRTUAL_WALLET)
            throw new RuntimeException("Solo para pagos en caja");

        PaymentResponse paymentResponse = paymentService.appointmentPayment(
                appointmentService.findAppointmentToWash(appointmentId));

        if(paymentResponse.getPaymentStatus() == PaymentStatus.APPROVED)
            appointmentService.confirmAppointment(appointmentId);

        appointment.setPayment(paymentResponse);
        return appointment;
    }

    @Transactional
    @Override
    public AppointmentResponse payOnlineAppointment(Long appointmentId){

        AppointmentResponse appointment = appointmentService.confirmAndSelectWashingStation(appointmentId);

        appointmentService.validateTransition(appointment.getStatus(), AppointmentStatus.CONFIRMED);

        if(appointment.getPayment().getPaymentMethod() != PaymentMethod.VIRTUAL_WALLET)
            throw new RuntimeException("Solo para pagos online");

        PaymentResponse paymentResponse = paymentService.appointmentPayment(
                appointmentService.findAppointmentToWash(appointmentId));

        appointmentService.validateTransition(appointment.getStatus(), AppointmentStatus.CONFIRMED);

        appointment.setPayment(paymentResponse);

        return appointment;
    }


    @Override
    public void handleMercadoPagoWebhook(Map<String, Object> response){
        System.out.println("Se comenzo con la notificacion con response " + response.toString());
        var data = (Map<String, Object>) response.get("data");
        if (data != null){

            Long id = Long.parseLong((String) data.get("id"));//extraemos el id del pago de marcado pago
            PaymentClient client = new PaymentClient();

            try{

                var payment = client.get(id, MPRequestOptions.builder().accessToken(MP_ACCESS_TOKEN).build());
                Long appointmentId = Long.parseLong(payment.getExternalReference());

                Appointment appointment = appointmentService.findAppointmentToWash(appointmentId);

                Payment paymentToSave = appointment.getPayment();
                paymentToSave.setWalletPaymentId(payment.getId());//guardar id de MP
                paymentToSave.setPaymentDate(LocalDateTime.now());

                if ("approved".equals(payment.getStatus())){
                    System.out.println("fue aprobrado");
                    appointmentService.confirmAppointment(appointmentId);
                    paymentToSave.setPaymentStatus(PaymentStatus.APPROVED);
                }else {
                    System.out.println("fue rechazado");
                    paymentToSave.setPaymentStatus(PaymentStatus.REJECTED);
                }
                paymentService.savePayment(paymentToSave);

            } catch (MPException | MPApiException e) {
                throw new PaymentProcessingException(e.getMessage(),e);
            }

        }

    }

}
