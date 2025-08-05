package com.cleandriver.service.implement;

import com.cleandriver.model.Customer;
import com.cleandriver.service.interfaces.ICustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService {
    @Override
    public Customer getCustomerByDni(String dni) {
        return null;
    }
}
