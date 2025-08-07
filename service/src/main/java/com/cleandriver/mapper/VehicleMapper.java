package com.cleandriver.mapper;


import com.cleandriver.dto.vehicle.VehicleRequest;
import com.cleandriver.dto.vehicle.VehicleResponse;
import com.cleandriver.dto.vehicle.VehicleSummary;
import com.cleandriver.model.Vehicle;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    Vehicle toVehicleFromRequest(VehicleRequest vehicle);

    VehicleResponse toVehicleResponse(Vehicle vehicle);

    VehicleSummary toSummary(Vehicle vehicle);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUpdate(VehicleRequest request, @MappingTarget Vehicle vehicle);


}
