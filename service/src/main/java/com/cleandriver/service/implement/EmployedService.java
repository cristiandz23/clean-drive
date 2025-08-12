package com.cleandriver.service.implement;

import com.cleandriver.dto.employed.EmployedRequest;
import com.cleandriver.dto.employed.EmployedResponse;
import com.cleandriver.exception.generalExceptions.ResourceNotFoundException;
import com.cleandriver.mapper.EmployedMapper;
import com.cleandriver.model.Employed;
import com.cleandriver.persistence.EmployedRepository;
import com.cleandriver.service.interfaces.IEmployedService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployedService implements IEmployedService {


    @Autowired
    private EmployedRepository employedRepository;

    @Autowired
    private EmployedMapper employedMapper;

    @Transactional
    @Override
    public void activateEmployed(Long employedId, boolean active) {
       employedRepository.updateIsActiveById(employedId,active);
    }


    @Override
    public EmployedResponse createEmployed(EmployedRequest employedRequest) {

        Employed employed = employedMapper.toEmployed(employedRequest);

        employed.setCreatedAt(LocalDate.now());

        return employedMapper.toEmployedResponse(employedRepository.save(employed));
    }

    @Override
    public EmployedResponse findEmployed(Long employedId) {
        return employedMapper.toEmployedResponse(findEmployedBy(employedId));
    }

    @Override
    public Employed findEmployedToWash(Long employedId) {

        Employed employed = this.findEmployedBy(employedId);
        if(!employed.isActive())
            throw new ResourceNotFoundException("El empleado no esta activo");

        return employed;
    }

    @Override
    public EmployedResponse findEmployed(String employedDni) {
        return employedMapper.toEmployedResponse(findEmployedBy(employedDni));
    }

    @Override
    public List<EmployedResponse> findAllEmployed() {
        return employedRepository.findAll().stream()
                .filter(Employed::isActive)
                .map(employedMapper::toEmployedResponse)
                .toList();
    }
    @Override
    public List<EmployedResponse> findAllActiveEmployed() {
        return employedRepository.findAll().stream()
                .map(employedMapper::toEmployedResponse)
                .toList();
    }
    @Override
    public void deleteEmployed(Long employedId) {

        Employed employed = findEmployedBy(employedId);
        employedRepository.delete(employed);

    }

    @Override
    public void deleteEmployed(String employedDni) {
        Employed employed = findEmployedBy(employedDni);
        employedRepository.delete(employed);
    }

    @Override
    public EmployedResponse updateEmployed(Long id, EmployedRequest employedRequest) {
        return null;
    }


    private Employed findEmployedBy(Long id){
        return employedRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontro el epleado con id: " + id)
        );
    }

    private Employed findEmployedBy(String employedDni){
        return employedRepository.findByDni(employedDni).orElseThrow(
                () -> new ResourceNotFoundException("No se encontro empleado con id: " + employedDni)
        );
    }

}
