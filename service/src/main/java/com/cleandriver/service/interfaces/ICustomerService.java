package com.cleandriver.service.interfaces;

import com.cleandriver.model.Customer;

public interface ICustomerService {

    Customer getCustomerByDni(String dni);

}
