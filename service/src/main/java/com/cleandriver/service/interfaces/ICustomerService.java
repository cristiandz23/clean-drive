package com.cleandriver.service.interfaces;

import com.cleandriver.dto.customer.CustomerRequest;
import com.cleandriver.dto.customer.CustomerResponse;
import com.cleandriver.dto.vehicle.VehicleRequest;
import com.cleandriver.model.Customer;

public interface ICustomerService {

    CustomerResponse createCustomer(CustomerRequest customer);

    Customer getCustomerByDni(String dni);

    CustomerResponse registerVehicle(String customerDni, VehicleRequest vehicle);


    CustomerResponse getCustomerResponseByDni(String dni);

    void deleteCustomer(String customerDni);
}
