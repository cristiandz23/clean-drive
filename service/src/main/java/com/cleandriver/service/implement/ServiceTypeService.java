package com.cleandriver.service.implement;

import com.cleandriver.model.ServiceType;
import com.cleandriver.model.enums.VehicleType;
import com.cleandriver.service.interfaces.IServiceTypeService;
import org.springframework.stereotype.Service;

@Service
public class ServiceTypeService implements IServiceTypeService {

    public void validateVehicleTypeCompatibility(ServiceType serviceType, VehicleType vehicleType) {
        if (!serviceType.getVehicleType().contains(vehicleType)) {
            throw new IllegalArgumentException("Vehicle type not compatible with service");
        }
    }

    @Override
    public ServiceType getServiceType(Long serviceTypeId) {
        return null;
    }

}
