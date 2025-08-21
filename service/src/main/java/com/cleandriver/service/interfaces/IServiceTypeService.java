package com.cleandriver.service.interfaces;

import com.cleandriver.dto.serviceType.ServiceTypeDto;
import com.cleandriver.model.ServiceType;
import com.cleandriver.model.enums.VehicleType;

import java.util.List;

public interface IServiceTypeService {

    ServiceTypeDto createServiceType(ServiceTypeDto serviceTypeDto);

    void validateVehicleTypeCompatibility(ServiceType serviceType, VehicleType vehicleType);

    ServiceType getServiceType(Long serviceTypeId);

    List<ServiceTypeDto> getServices();

    void deleteServiceType(Long id);

    void enableServiceType(Long id);

    void disableServiceType(Long id);
}
