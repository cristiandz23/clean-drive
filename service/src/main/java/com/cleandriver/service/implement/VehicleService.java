package com.cleandriver.service.implement;

import com.cleandriver.dto.vehicle.VehicleRequest;
import com.cleandriver.dto.vehicle.VehicleResponse;
import com.cleandriver.exception.generalExceptions.ResourceNotFoundException;
import com.cleandriver.mapper.VehicleMapper;
import com.cleandriver.model.Customer;
import com.cleandriver.model.Vehicle;
import com.cleandriver.persistence.VehicleRepository;
import com.cleandriver.service.interfaces.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class VehicleService implements IVehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private VehicleMapper vehicleMapper;


    private Vehicle findVehicleByPlateNumber(String plateNumber){
        return vehicleRepository.findByPlateNumber(plateNumber).orElseThrow(
                ()-> new ResourceNotFoundException("No se encontro vehicle con patente " + plateNumber)
        );
    }

    @Override
    public VehicleResponse registerVehicle(VehicleRequest vehicle, Customer customer) {

        if(vehicleRepository.existsByPlateNumber(vehicle.getPlateNumber())){
            Vehicle newVehicle = this.findVehicleByPlateNumber(vehicle.getPlateNumber());
            vehicleMapper.toUpdate(vehicle,newVehicle);
            newVehicle.setCustomer(customer);
            return vehicleMapper.toVehicleResponse(vehicleRepository.save(newVehicle));
        }


        Vehicle newVehicle = vehicleMapper.toVehicleFromRequest(vehicle);
        newVehicle.setCustomer(customer);
        newVehicle.setCreatedAt(LocalDate.now());

        return vehicleMapper.toVehicleResponse(vehicleRepository.save(newVehicle));
    }
    @Override
    public Vehicle regiterVehicleByExpressAppointment(VehicleRequest vehicle){
        Vehicle newVehicle = vehicleMapper.toVehicleFromRequest(vehicle);
        newVehicle.setCreatedAt(LocalDate.now());

        return vehicleRepository.save(newVehicle);
    }

    @Override
    public Vehicle findVehicleOrNullByPlateNumber(String plateNumber) {
        return vehicleRepository.findByPlateNumber(plateNumber).orElse(null);
    }

}
