package com.controller;


import com.cleandriver.dto.customer.CustomerRequest;
import com.cleandriver.dto.customer.CustomerResponse;
import com.cleandriver.dto.serviceType.ServiceTypeDto;
import com.cleandriver.dto.vehicle.VehicleRequest;
import com.cleandriver.service.interfaces.ICustomerService;
import com.cleandriver.service.interfaces.IServiceTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @PostMapping("create")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Valid
                                                            CustomerRequest customer){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService
                .createCustomer(customer));
    }

    @PutMapping("/register-vehicle/{customerDni}")
    public ResponseEntity<CustomerResponse> registerVehicle(@PathVariable String customerDni,
                                                            @RequestBody @Valid VehicleRequest vehicle){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService
                .registerVehicle(customerDni,vehicle));
    }

}
