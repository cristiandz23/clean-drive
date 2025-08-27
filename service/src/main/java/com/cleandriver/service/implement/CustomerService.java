package com.cleandriver.service.implement;

import com.cleandriver.dto.customer.CustomerRequest;
import com.cleandriver.dto.customer.CustomerResponse;
import com.cleandriver.dto.vehicle.VehicleRequest;
import com.cleandriver.mapper.CustomerMapper;
import com.cleandriver.model.Customer;
import com.cleandriver.persistence.CustomerRepository;
import com.cleandriver.service.interfaces.ICustomerService;
import com.cleandriver.service.interfaces.IVehicleService;
import com.cleandriver.service.interfaces.appointment.IAppointmentService;
import com.cleandriver.service.interfaces.appointment.IAppointmentStatsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private IAppointmentStatsService appointmentStatsService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private IVehicleService vehicleService;

    private Customer findCustomer(String dni){
        return customerRepository.findByDni(dni).orElseThrow(
                () -> new RuntimeException("No se encontro customer con dni: " + dni)
        );

    }


    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {

        Customer customer = customerMapper.toCustomerFromRequest(customerRequest);
        customer.setRegisteredAt(LocalDate.now());


        return customerMapper.toResponse(customerRepository.save(customer));

    }

    @Override
    public Customer getCustomerByDni(String dni) {
        return this.findCustomer(dni);
    }

    @Override

    public CustomerResponse registerVehicle(String customerDni, VehicleRequest vehicle) {

        if(customerDni==null || customerDni.isBlank())
            throw new RuntimeException("Debe indicar un customer para registrar el vehiculo");

        Customer customer = this.findCustomer(customerDni);

        vehicleService.registerVehicle(vehicle,customer);

        return customerMapper.toResponse(customer);
    }

    @Override
    public CustomerResponse getCustomerResponseByDni(String dni) {
        return customerMapper.toResponse(this.findCustomer(dni));
    }

    @Override
    @Transactional
    public void deleteCustomer(String customerDni) {
        Customer customer = this.findCustomer(customerDni);

        if(appointmentStatsService.hasPendingAppointments(customer.getId()))
            throw new RuntimeException("No se puede eliminar porque tiene turnos sin resolver\nCustomer: " + customer.getDni());

        appointmentStatsService.detachCustomerFromAppointments(customer.getId());

        customer.getVehicles().forEach( ve -> ve.setCustomer(null));

        customerRepository.delete(customer);

    }

}
