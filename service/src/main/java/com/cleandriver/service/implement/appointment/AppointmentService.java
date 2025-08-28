package com.cleandriver.service.implement.appointment;

import com.cleandriver.dto.appointment.AppointmentRequest;
import com.cleandriver.dto.appointment.AppointmentResponse;
import com.cleandriver.dto.appointment.ExpressAppointmentRequest;
import com.cleandriver.exception.appointmentExceptions.InvalidTransitionException;
import com.cleandriver.exception.customerException.CustomerHasNotVehiclesException;
import com.cleandriver.exception.customerException.CustomerVehicleNotFoundException;
import com.cleandriver.exception.generalExceptions.ResourceNotFoundException;
import com.cleandriver.exception.vehicleException.NoIndicatedPlateNumber;
import com.cleandriver.exception.vehicleException.VehicleAlreadyAnAppointmentException;
import com.cleandriver.exception.washingStationException.NotAvailableWashingStationException;
import com.cleandriver.mapper.AppointmentMapper;
import com.cleandriver.model.*;
import com.cleandriver.model.enums.AppointmentStatus;
import com.cleandriver.model.enums.PaymentMethod;
import com.cleandriver.model.enums.PaymentStatus;
import com.cleandriver.persistence.AppointmentRepository;
import com.cleandriver.service.interfaces.*;
import com.cleandriver.service.interfaces.appointment.IAppointmentService;
import com.cleandriver.service.interfaces.promotion.IPromotionService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;


@Slf4j
@Service
public class AppointmentService implements IAppointmentService {


    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private IWashingStationService washingStationService;

    @Autowired
    private IServiceTypeService serviceTypeService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IVehicleService vehicleService;

    @Autowired
    private IPromotionService promotionService;

    @Autowired
    private AppointmentMapper appointmentMapper;



    @Override
    public AppointmentResponse findAppointment(Long appointmentId) {
        return appointmentMapper.toResponse(this.findAppointmentById(appointmentId));
    }

    @Override
    public Appointment findAppointmentToWash(Long appointmentId) {
        return this.findAppointmentById(appointmentId);
    }

    @Override
    public AppointmentResponse confirmAndSelectWashingStation(Long appointmentId){

        Appointment appointment = this.findAppointmentToWash(appointmentId);

        List<WashingStation> stations = washingStationService.getAvailableWashingStationOnAppointment(appointment.getStartDateTime(),appointment.getEndDateTime());

        if(stations.isEmpty())
            throw new RuntimeException("no hay estaciones libres");

        if(stations.contains(appointment.getWashingStation()))
            return appointmentMapper.toResponse(appointment);

        appointment.setWashingStation(resolveWashingStation(stations));

        return appointmentMapper.toResponse(appointmentRepository.save(appointment));
    }



    @Override
    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest appointmentRequest) {

        if(appointmentRequest.getDateTime().isBefore(LocalDateTime.now()))
            throw new RuntimeException("bad request la fecha es pasada");

        Customer customer = customerService.getCustomerByDni(appointmentRequest.getCustomerDni());

        Vehicle vehicle = this.getVehicleFromCustomer(customer, appointmentRequest.getPlateNumber());

        ServiceType serviceType = serviceTypeService.getServiceType(appointmentRequest.getServiceType());

        LocalDateTime startAppointment = appointmentRequest.getDateTime();
        LocalDateTime endAppointment = appointmentRequest.getDateTime().plusMinutes(serviceType.getDurationInMinutes());

        if(appointmentRepository.hasAppointmentInRangeByVehiclePlate(vehicle.getPlateNumber(), startAppointment,endAppointment)==1)
            throw new RuntimeException("Este auto ya tiene un turno dentro de este rango horario");

        List<WashingStation> washingStations = washingStationService.getAvailableWashingStationOnAppointment(startAppointment, endAppointment);

        WashingStation selectedStation = this.resolveWashingStation(appointmentRequest.getWashingStationId(), washingStations);

        serviceTypeService.validateVehicleTypeCompatibility(serviceType, vehicle.getVehicleType());


        Appointment appointment = this.buildAppointment(
                customer,
                appointmentRequest.getPaymentMethod(),
                serviceType,
                startAppointment,
                endAppointment,
                selectedStation,
                vehicle
        );
        appointmentRepository.save(appointment);
        if(appointmentRequest.getPromotion() != null)
            this.applyPromotion(appointmentRequest.getPromotion(), appointment);
        return appointmentMapper.toResponse(appointmentRepository.save(appointment));
    }

    @Override
    @Transactional
    public AppointmentResponse createExpressAppointment(ExpressAppointmentRequest appointmentRequest) {

        if(appointmentRequest.getDateTime().isBefore(LocalDateTime.now()))
            throw new RuntimeException("bad request la fecha es pasada");

        ServiceType serviceType = serviceTypeService.getServiceType(appointmentRequest.getServiceType());

        LocalDateTime startAppointment = appointmentRequest.getDateTime();
        LocalDateTime endAppointment = appointmentRequest.getDateTime().plusMinutes(serviceType.getDurationInMinutes());


        List<WashingStation> stations = washingStationService.getAvailableWashingStationOnAppointment(startAppointment, endAppointment); // descomentar la linea que hace que los turnos deban estar pagados

        WashingStation selectedStation = this.resolveWashingStation(appointmentRequest.getWashingStation(), stations);

        serviceTypeService.validateVehicleTypeCompatibility(serviceType, appointmentRequest.getVehicle().getVehicleType());

        Vehicle vehicle = vehicleService.findVehicleOrNullByPlateNumber(appointmentRequest.getVehicle().getPlateNumber());
        if (vehicle == null) {
            vehicle = vehicleService.regiterVehicleByExpressAppointment(appointmentRequest.getVehicle());

        }

        if(appointmentRepository.hasAppointmentInRangeByVehiclePlate(vehicle.getPlateNumber(),
                startAppointment,endAppointment) == 1)
            throw new VehicleAlreadyAnAppointmentException("Este auto ya tiene un turno dentro de este rango horario");


        Appointment appointment = this.buildAppointment(
                null,
                appointmentRequest.getPayment(),
                serviceType,
                startAppointment,
                endAppointment,
                selectedStation,
                vehicle
        );
        appointmentRepository.save(appointment);
        if(appointmentRequest.getPromotion() != null){
            this.applyPromotion(appointmentRequest.getPromotion(), appointment);

        }

        return appointmentMapper.toResponse(appointmentRepository.save(appointment));
    }


    @Override
    public void deleteAppointment(Long appointmentId) {
        Appointment appointment = findAppointmentById(appointmentId);

        if(!(appointment.getStatus() == AppointmentStatus.CANCELED || appointment.getStatus() == AppointmentStatus.COMPLETED)){
            throw new InvalidTransitionException("No se puede eliminar turno que no fue cancelado o completado");
        }

        appointmentRepository.deleteById(appointmentId);
    }

    @Override
    public AppointmentResponse confirmAppointment(Long appointmentId) {
        return appointmentMapper.toResponse(setAppointmentStatus(appointmentId,AppointmentStatus.CONFIRMED));
    }

    @Override
    public void cancelAppointment(Long appointmentId) {
        appointmentMapper.toResponse(setAppointmentStatus(appointmentId, AppointmentStatus.CANCELED));
    }

    @Override
    public void initAppointment(Long appointmentId) {
        appointmentMapper.toResponse(setAppointmentStatus(appointmentId, AppointmentStatus.IN_PROGRESS));
    }

    @Override
    public void finishAppointment(Long appointmentId) {
        appointmentMapper.toResponse(setAppointmentStatus(appointmentId, AppointmentStatus.COMPLETED));
    }

    @Override
    public AppointmentResponse updateAppointment(Long appointmentId, AppointmentRequest appointmentRequest) {

//        Appointment appointment = this.findAppointmentById(appointmentId);
//
//
//        // Actualizar fecha y hora
//        if (appointmentRequest.getDateTime() != null) {
//            LocalDateTime start = appointmentRequest.getDateTime();
//            LocalDateTime end = start.plusMinutes(appointment.getServiceType().getDurationInMinutes());
//
//            // Validar si la estación está libre
//            boolean disponible = washingStationService
//                    .isAvailable(appointment.getWashingStation(), start, end, appointment.getId());
//            if (!disponible)
//                throw new RuntimeException("La estación no está disponible en ese horario");
//
//            appointment.setStartDateTime(start);
//            appointment.setEndDateTime(end);
//        }
//
//        // Cambiar tipo de servicio si aplica
//        if (request.getServiceTypeId() != null) {
//            ServiceType serviceType = serviceTypeRepository.findById(request.getServiceTypeId())
//                    .orElseThrow(() -> new RuntimeException("Tipo de servicio no encontrado"));
//            appointment.setServiceType(serviceType);
//            appointment.getPayment().setAmount(serviceType.getPrice());
//        }
//
//        // Cambiar estado si aplica
//        if (request.getStatus() != null) {
//            appointment.setStatus(request.getStatus());
//        }
//
//        return appointmentMapper.toResponse(appointmentRepository.save(appointment));
        return null;
    }

    @Override
    public void validateTransition(AppointmentStatus current, AppointmentStatus next){
        if(!isValidTransition(current,next))
            throw new InvalidTransitionException("El turno ya esta confirmado");
    }

    private void applyPromotion(Long promotionId, Appointment appointment){

        BigDecimal finalPrice = promotionService.applyPromotion(promotionId,appointment);

        appointment.getPayment().setAmount(finalPrice);

    }

    private Appointment buildAppointment(Customer customer,
                                         PaymentMethod paymentMethod,
                                         ServiceType serviceType,
                                         LocalDateTime start,
                                         LocalDateTime end,
                                         WashingStation station,
                                         Vehicle vehicle) {

        return Appointment.builder()
                .customer(customer)
                .serviceType(serviceType)
                .payment(Payment.builder()
                        .amount(serviceType.getPrice())
                        .createdAt(LocalDateTime.now())
                        .paymentStatus(PaymentStatus.PENDING)
                        .paymentMethod(paymentMethod)
                        .build())
                .startDateTime(start)
                .endDateTime(end)
                .status(AppointmentStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .washingStation(station)
                .vehicleToWash(vehicle)
                .build();
    }

    private WashingStation resolveWashingStation(List<WashingStation> stations){
        return pickRandomStation(stations);
    }

    private WashingStation resolveWashingStation(Long washingStationId, List<WashingStation> stations) {
        if (washingStationId == null) {
            return pickRandomStation(stations);
        }

        return stations.stream()
                .filter(s -> s.getId().equals(washingStationId))
                .findFirst()
                .orElseThrow(() -> new NotAvailableWashingStationException("Specified station not available"));
    }


    private WashingStation pickRandomStation(List<WashingStation> stations) {
        if(stations.isEmpty())
            throw new NotAvailableWashingStationException("No hay estaciones disponibles");
        return stations.get(ThreadLocalRandom.current().nextInt(stations.size()));
    }

    private Appointment findAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No se encontro appointment con id: " + id));
    }




    private boolean isValidTransition(AppointmentStatus current, AppointmentStatus next) {
        return switch (current) {
            case CREATED -> List.of(AppointmentStatus.CONFIRMED).contains(next);
            case CONFIRMED -> List.of( AppointmentStatus.CANCELED,AppointmentStatus.IN_PROGRESS).contains(next);
            case IN_PROGRESS -> List.of(AppointmentStatus.COMPLETED,AppointmentStatus.CANCELED).contains(next);//ANALIZAR SI ES POSIBLE CANCEAR Y APLICAR LA LOGICA CORRESPONDIENTE
//            case COMPLETED -> Objects.equals(AppointmentStatus.IN_PROGRESS, next);
            case CANCELED -> List.of(AppointmentStatus.CONFIRMED).contains(next);
            case NO_SHOW -> Objects.equals(AppointmentStatus.CONFIRMED, next);
            default -> false;

        };
    }


    private Vehicle getVehicleFromCustomer(Customer customer, String plateNumber) {

        List<Vehicle> vehicles = customer.getVehicles();

        List<String> plateNumbers = new ArrayList<>();
        vehicles.forEach(ve -> plateNumbers.add(ve.getPlateNumber()));

        if (plateNumber == null)
            throw new NoIndicatedPlateNumber("No indico ningun numero de patente");

        if (vehicles.isEmpty())
            throw new CustomerHasNotVehiclesException("El cliente " + customer.getName() + " "
                    + customer.getLastName() + " No tiene vehiculos registrados");

        if (!plateNumbers.contains(plateNumber))
            throw new CustomerVehicleNotFoundException("La patente: " + plateNumber + "no corresponde a los vehiculos registrados del cliente"
                    + customer.getName() + " " + customer.getLastName());


        return vehicles.stream()
                .filter(ve -> ve.getPlateNumber().equals(plateNumber))
                .findFirst()
                .orElseThrow(() -> new CustomerVehicleNotFoundException("No se encontro patente: " + plateNumber +
                        " relacionada con el cliente: " + customer.getName() +
                        " " + customer.getLastName()));

    }

    private Appointment setAppointmentStatus(Long appointmentId, AppointmentStatus status){
        Appointment appointment = findAppointmentById(appointmentId);
        if(!isValidTransition(appointment.getStatus(),status))
            throw new InvalidTransitionException("Is not valid transition from "
                    + appointment.getStatus() + " to " +status);
        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }
}
