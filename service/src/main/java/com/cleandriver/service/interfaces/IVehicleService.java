package com.cleandriver.service.interfaces;

import com.cleandriver.dto.vehicle.VehicleRequest;
import com.cleandriver.dto.vehicle.VehicleResponse;
import com.cleandriver.model.Customer;
import com.cleandriver.model.Vehicle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IVehicleService {

    VehicleResponse registerVehicle(VehicleRequest vehicle, Customer customer);

    Vehicle regiterVehicleByExpressAppointment(VehicleRequest vehicle);

    Vehicle findVehicleOrNullByPlateNumber(String plateNumber);

    VehicleResponse findByPlateNumber(String plateNumber);

    VehicleResponse updateVehicle(String plateNumber, VehicleRequest vehicleRequest);
}
