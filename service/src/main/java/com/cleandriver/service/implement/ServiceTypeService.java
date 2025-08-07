package com.cleandriver.service.implement;

import com.cleandriver.dto.serviceType.ServiceTypeDto;
import com.cleandriver.mapper.ServiceTypeMapper;
import com.cleandriver.model.ServiceType;
import com.cleandriver.model.enums.VehicleType;
import com.cleandriver.persistence.ServiceTypeRepository;
import com.cleandriver.service.interfaces.IServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ServiceTypeService implements IServiceTypeService {

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Autowired
    ServiceTypeMapper serviceTypeMapper;

    @Override
    public ServiceTypeDto createServiceType(ServiceTypeDto serviceTypeDto) {

        ServiceType service = serviceTypeMapper.toServiceType(serviceTypeDto);
        service.setCreatedAt(LocalDate.now());

        return serviceTypeMapper.toServiceTypeDto(serviceTypeRepository.save(service));
    }

    public void validateVehicleTypeCompatibility(ServiceType serviceType, VehicleType vehicleType) {
        if (!serviceType.getVehicleType().contains(vehicleType)) {
            throw new IllegalArgumentException("Vehicle type not compatible with service");
        }
    }

    @Override
    public ServiceType getServiceType(Long serviceTypeId) {
       return  serviceTypeRepository.findById(serviceTypeId).orElseThrow(
                () -> new RuntimeException("no se encontro service type")
        );

    }

}
