package com.cleandriver.service.implement;

import com.cleandriver.dto.serviceType.ServiceTypeDto;
import com.cleandriver.exception.generalExceptions.ResourceNotFoundException;
import com.cleandriver.exception.serviceTypeException.IncompatibleServiceTypeException;
import com.cleandriver.mapper.ServiceTypeMapper;
import com.cleandriver.model.ServiceType;
import com.cleandriver.model.enums.VehicleType;
import com.cleandriver.persistence.ServiceTypeRepository;
import com.cleandriver.service.interfaces.IServiceTypeService;
import com.cleandriver.service.interfaces.appointment.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ServiceTypeService implements IServiceTypeService {

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Autowired
    private ServiceTypeMapper serviceTypeMapper;

    @Autowired
    private IAppointmentService appointmentService;

    @Override
    public ServiceTypeDto createServiceType(ServiceTypeDto serviceTypeDto) {

        ServiceType service = serviceTypeMapper.toServiceType(serviceTypeDto);
        service.setCreatedAt(LocalDate.now());

        return serviceTypeMapper.toServiceTypeDto(serviceTypeRepository.save(service));
    }
    @Override
    public void validateVehicleTypeCompatibility(ServiceType serviceType, VehicleType vehicleType) {
        if (!serviceType.getVehicleType().contains(vehicleType)) {
            throw new IncompatibleServiceTypeException("Vehicle type " + vehicleType +" not compatible with service " +serviceType );
        }
    }

    @Override
    public ServiceType getServiceType(Long serviceTypeId) {
       return  serviceTypeRepository.findById(serviceTypeId).orElseThrow(
                () -> new ResourceNotFoundException("no se encontro service type con id " + serviceTypeId)
        );

    }

    @Override
    public List<ServiceTypeDto> getServices() {
        return serviceTypeRepository.findAll()
                .stream()
                .map(serviceTypeMapper::toServiceTypeDto)
                .toList();
    }

    @Override
    public void deleteServiceType(Long id) {

        ServiceType serviceType = this.findServiceType(id);

//        if (appointmentService.existsAppointmentWithServiceType(serviceType.getId()))
//            throw new RuntimeException("No se puede eliminar porque hay turnos activos con este tipo de servicio");
        serviceTypeRepository.delete(serviceType);
    }

    private ServiceType findServiceType(Long id){
        return serviceTypeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se econtro el tipo de servicio id: " + id)
        );
    }

}
