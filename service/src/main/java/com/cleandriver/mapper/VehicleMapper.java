package com.cleandriver.mapper;


import com.cleandriver.dto.vehicle.VehicleRequest;
import com.cleandriver.dto.vehicle.VehicleResponse;
import com.cleandriver.dto.vehicle.VehicleSummary;
import com.cleandriver.model.Vehicle;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    Vehicle toVehicleFromRequest(VehicleRequest vehicle);

    VehicleResponse toVehicleResponse(Vehicle vehicle);

    VehicleSummary toSummary(Vehicle vehicle);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Vehicle toUpdate(VehicleRequest request, @MappingTarget Vehicle vehicle);

    @Condition
    default boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }

}
