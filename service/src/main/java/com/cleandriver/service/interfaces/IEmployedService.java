package com.cleandriver.service.interfaces;

import com.cleandriver.dto.employed.EmployedRequest;
import com.cleandriver.dto.employed.EmployedResponse;
import com.cleandriver.model.Employed;

import java.util.List;

public interface IEmployedService {


    void activateEmployed(Long employedId,boolean active);


    EmployedResponse createEmployed(EmployedRequest employedRequest);

    EmployedResponse findEmployed(Long employedId);

    Employed findEmployedToWash(Long employedId);

    EmployedResponse findEmployed(String employedDni);

    List<EmployedResponse> findAllEmployed();

    List<EmployedResponse> findAllActiveEmployed();

    void deleteEmployed (Long employedId);

    void deleteEmployed(String employedDni);

    EmployedResponse updateEmployed(Long id, EmployedRequest employedRequest);



}
