package com.cleandriver.service.implement;

import com.cleandriver.dto.appointment.AppointmentRequest;
import com.cleandriver.dto.appointment.AppointmentResponse;
import com.cleandriver.dto.appointment.ExpressAppointmentRequest;
import com.cleandriver.mapper.AppointmentMapper;
import com.cleandriver.mapper.WashingStationMapper;
import com.cleandriver.model.*;
import com.cleandriver.model.enums.AppointmentStatus;
import com.cleandriver.model.enums.PaymentMethod;
import com.cleandriver.model.enums.PaymentStatus;
import com.cleandriver.persistence.AppointmentRepository;
import com.cleandriver.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;


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
    private AppointmentMapper appointmentMapper;

    @Autowired
    private WashingStationMapper washingStationMapper;


    @Override
    public AppointmentResponse findAppointment(Long appointmentId) {
        return appointmentMapper.toResponse(this.findAppointmentById(appointmentId));
    }

    @Override
    public Appointment findAppointmentToWash(Long appointmentId) {
        return this.findAppointmentById(appointmentId);
    }


    @Override
    public List<AppointmentResponse> findTodayAppointments() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1); // inicio del día siguiente

        return appointmentRepository.findAllByStartDateTimeBetween(start, end)
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    @Override
    public List<AppointmentResponse> findAppointmentsByDate(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = start.plusDays(1); // Fin del día

        return appointmentRepository.findAllByStartDateTimeBetween(start, end)
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    @Override
    public List<AppointmentResponse> findCustomerAppointments(String customerDni) {

        return appointmentRepository.findAllByCustomer_Dni(customerDni)
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    @Override
    public List<AppointmentResponse> findCustomerAppointments(String customerDni, AppointmentStatus appointmentStatus) {

        return appointmentRepository.findAllByCustomer_Dni(customerDni)
                .stream()
                .filter(appointment -> appointment.getStatus().equals(appointmentStatus))
                .map(appointmentMapper::toResponse)
                .toList();
    }

    @Override
    public AppointmentResponse createAppointment(AppointmentRequest appointmentRequest) {


        Customer customer = customerService.getCustomerByDni(appointmentRequest.getCustomerDni());

        Vehicle vehicle = this.getVehicleFromCustomer(customer, appointmentRequest.getPlateNumber());

        ServiceType serviceType = serviceTypeService.getServiceType(appointmentRequest.getServiceType());

        LocalDateTime startAppointment = appointmentRequest.getDateTime();
        LocalDateTime endAppointment = appointmentRequest.getDateTime().plusMinutes(serviceType.getDurationInMinutes());

        if(appointmentRepository.hasAppointmentInRangeByVehiclePlate(vehicle.getPlateNumber(), startAppointment,endAppointment)==1)
            throw new RuntimeException("Este auto ya tiene un turno dentro de este rango horario");

        List<WashingStation> washingStations = this.getAvailableWashingStationOnAppointment(startAppointment, endAppointment);

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

        return appointmentMapper.toResponse(appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentResponse createExpressAppointment(ExpressAppointmentRequest appointmentRequest) {

        ServiceType serviceType = serviceTypeService.getServiceType(appointmentRequest.getServiceType());

        LocalDateTime startAppointment = appointmentRequest.getDateTime();
        LocalDateTime endAppointment = appointmentRequest.getDateTime().plusMinutes(serviceType.getDurationInMinutes());

        List<WashingStation> stations = this.getAvailableWashingStationOnAppointment(startAppointment, endAppointment);

        WashingStation selectedStation = this.resolveWashingStation(appointmentRequest.getWashingStation(), stations);

        serviceTypeService.validateVehicleTypeCompatibility(serviceType, appointmentRequest.getVehicle().getVehicleType());

        Vehicle vehicle = vehicleService.findVehicleOrNullByPlateNumber(appointmentRequest.getVehicle().getPlateNumber());
        if (vehicle == null) {
            vehicle = vehicleService.regiterVehicleByExpressAppointment(appointmentRequest.getVehicle());

        }

        if(appointmentRepository.hasAppointmentInRangeByVehiclePlate(vehicle.getPlateNumber(),
                startAppointment,endAppointment) == 1)
            throw new RuntimeException("Este auto ya tiene un turno dentro de este rango horario");


        Appointment appointment = this.buildAppointment(
                null,
                appointmentRequest.getPayment(),
                serviceType,
                startAppointment,
                endAppointment,
                selectedStation,
                vehicle
        );

        return appointmentMapper.toResponse(appointmentRepository.save(appointment));
    }


    @Override
    public void deleteAppointment(Long appointmentId) {
        Appointment appointment = findAppointmentById(appointmentId);

        if(!(appointment.getStatus() == AppointmentStatus.CANCELED || appointment.getStatus() == AppointmentStatus.COMPLETED)){
            throw new RuntimeException("No se puede eliminar turno que no fue cancelado o completado");
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

    public int getWashAmountByDateAndPlantNumber(String plateNumber, int weekRange){

        return appointmentRepository.findByVehicleToWash_PlateNumberAndStartDateTimeAfter(plateNumber,
                LocalDateTime.now().minusWeeks(weekRange)).stream()
                .filter(app -> app.getStatus().equals(AppointmentStatus.COMPLETED))
                .toList().size();
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

    private WashingStation resolveWashingStation(Long washingStationId, List<WashingStation> stations) {
        if (washingStationId == null) {
            return pickRandomStation(stations);
        }

        return stations.stream()
                .filter(s -> s.getId().equals(washingStationId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Specified station not available"));
    }


    private WashingStation pickRandomStation(List<WashingStation> stations) {
        return stations.get(ThreadLocalRandom.current().nextInt(stations.size()));
    }

    private Appointment findAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(() ->
                new RuntimeException("No se encontro appointment con id: " + id));
    }

    private boolean isValidTransition(AppointmentStatus current, AppointmentStatus next) {
        return switch (current) {
            case CREATED -> Objects.equals(AppointmentStatus.CANCELED, next);
            case CONFIRMED -> List.of(AppointmentStatus.CREATED, AppointmentStatus.CANCELED).contains(next);
            case IN_PROGRESS ->
                    Objects.equals(AppointmentStatus.CONFIRMED, next);//ANALIZAR SI ES POSIBLE CANCEAR Y APLICAR LA LOGICA CORRESPONDIENTE
            case COMPLETED -> Objects.equals(AppointmentStatus.IN_PROGRESS, next);
            case CANCELED -> List.of(AppointmentStatus.CONFIRMED, AppointmentStatus.CREATED).contains(next);
            case NO_SHOW -> Objects.equals(AppointmentStatus.CONFIRMED, next);
            default -> false;

        };
    }

    private List<WashingStation> getAvailableWashingStationOnAppointment(
            LocalDateTime startAppointment, LocalDateTime endAppointment
    ) {

        List<WashingStation> washingStations = washingStationService.getAvailableStations(startAppointment, endAppointment);
//                .stream()
//                .map(washingStationMapper::toWashingStation)
//                .toList();

        if (washingStations.isEmpty())
            throw new RuntimeException("there are not available appointments");
        return washingStations;
    }

    private Vehicle getVehicleFromCustomer(Customer customer, String plateNumber) {

        List<Vehicle> vehicles = customer.getVehicles();

        List<String> plateNumbers = new ArrayList<>();
        vehicles.forEach(ve -> plateNumbers.add(ve.getPlateNumber()));

        if (plateNumber == null)
            throw new RuntimeException("No indico ningun numero de patente");

        if (vehicles.isEmpty())
            throw new RuntimeException("El cliente " + customer.getName() + " "
                    + customer.getLastName() + " No tiene vehiculos registrados");

        if (!plateNumbers.contains(plateNumber))
            throw new RuntimeException("La patente: " + plateNumber + "no corresponde a los vehiculos registrados del cliente"
                    + customer.getName() + " " + customer.getLastName());


        return vehicles.stream()
                .filter(ve -> ve.getPlateNumber().equals(plateNumber))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontro patente: " + plateNumber +
                        " relacionada con el cliente: " + customer.getName() +
                        " " + customer.getLastName()));

    }

    private Appointment setAppointmentStatus(Long appointmentId, AppointmentStatus status){
        Appointment appointment = findAppointmentById(appointmentId);
        if(!isValidTransition(appointment.getStatus(),status))
            throw new RuntimeException("Is not valid transition");
        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }
}
