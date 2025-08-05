package com.cleandriver.service.interfaces;

import com.cleandriver.model.ServiceType;
import com.cleandriver.model.enums.VehicleType;

public interface IServiceTypeService {

    void validateVehicleTypeCompatibility(ServiceType serviceType, VehicleType vehicleType);

    ServiceType getServiceType(Long serviceTypeId);

}
