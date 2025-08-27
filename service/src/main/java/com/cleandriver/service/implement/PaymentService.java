package com.cleandriver.service.implement;

import com.cleandriver.dto.payment.PaymentResponse;
import com.cleandriver.exception.paymentException.PaymentProcessingException;
import com.cleandriver.mapper.PaymentMapper;
import com.cleandriver.model.Appointment;
import com.cleandriver.model.Payment;
import com.cleandriver.model.enums.PaymentMethod;
import com.cleandriver.model.enums.PaymentStatus;
import com.cleandriver.persistence.PaymentRepository;
import com.cleandriver.service.interfaces.IPaymentService;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.cleandriver.configuration.ApplicationEnvironment.MP_ACCESS_TOKEN;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMapper paymentMapper;


    @Override
    public PaymentResponse appointmentPayment(Appointment appointment) {

        PaymentResponse payment;

        switch (appointment.getPayment().getPaymentMethod()){

            case PaymentMethod.CASH, PaymentMethod.BANK_TRANSFER -> payment = payWihCash(appointment);

            case VIRTUAL_WALLET -> payment = payWithMp(appointment);

            default -> throw new RuntimeException("No indico un metodo de pago valido");
        }


        return payment;
    }

//    public Payment doPaymentWhenIsFree(Appointment appointment){
//
//
//
//    }


    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    private PaymentResponse payWithMp(Appointment appointment) {
        PaymentResponse payment = paymentMapper.toResponse(appointment.getPayment());
        if(payment.getPaymentStatus() != PaymentStatus.PENDING)
            throw new RuntimeException("Turno ya pagado");
        payment.setPaymentDate(LocalDateTime.now());
        payment.setUrl(createPreference(appointment).getInitPoint());

        return payment;
    }

    private PaymentResponse payWihCash(Appointment appointment) {

        Payment payment = appointment.getPayment();
        if(payment.getPaymentStatus() != PaymentStatus.PENDING)
            throw new RuntimeException("Turno ya pagado");

        payment.setAmount(appointment.getServiceType().getPrice());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.APPROVED);


        return paymentMapper.toResponse(paymentRepository.save(payment));
    }


    private Preference createPreference(Appointment appointment) {
        List<PreferenceItemRequest> items = new ArrayList<>();
        PreferenceItemRequest preference = PreferenceItemRequest.builder()
                .id(appointment.getId().toString())
                .title(appointment.getServiceType().getName())
                .quantity(1)
                .unitPrice(appointment.getServiceType().getPrice())
                .build();
        items.add(preference);
        PreferenceBackUrlsRequest urls = PreferenceBackUrlsRequest.builder()
                .success("asd")//CONFIGURAR ESTAS URLS
                .failure("")
                .build();


        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .binaryMode(true)
                .items(items)
                .externalReference(String.valueOf(appointment.getId()))
                .notificationUrl("https://8f121d09b904.ngrok-free.app/api/v1/payment/web")
                .marketplace("Clean driver")
                .metadata(Map.of("appointmentId",appointment.getId()))
                .build();

        PreferenceClient preferenceClient = new PreferenceClient();
        MPRequestOptions mpRequestOptions = MPRequestOptions.builder()
                .accessToken(MP_ACCESS_TOKEN)
                .build();

        try {
            return preferenceClient.create(preferenceRequest,mpRequestOptions);
        } catch (MPException | MPApiException e) {

            if(e instanceof MPApiException){
                System.out.print("\n headers: " + ((MPApiException) e).getApiResponse().getHeaders()
                        + "\n causa: "  + ((MPApiException) e).getMessage()
                + "\n content: " + ((MPApiException) e).getApiResponse().getContent());
            }else {
                System.out.print(((MPException) e).getMessage());
            }

            throw new PaymentProcessingException("Error al crear la preferencia de pago causa " + e.getMessage(),e);
        }

    }

}
