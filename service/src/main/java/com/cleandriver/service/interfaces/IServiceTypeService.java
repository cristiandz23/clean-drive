package com.cleandriver.service.interfaces;

import com.cleandriver.dto.serviceType.ServiceTypeDto;
import com.cleandriver.model.ServiceType;
import com.cleandriver.model.enums.VehicleType;

public interface IServiceTypeService {

    ServiceTypeDto createServiceType(ServiceTypeDto serviceTypeDto);

    void validateVehicleTypeCompatibility(ServiceType serviceType, VehicleType vehicleType);

    ServiceType getServiceType(Long serviceTypeId);

}
