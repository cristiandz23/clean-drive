package com.cleandriver.mapper;

import com.cleandriver.dto.customer.CustomerRequest;
import com.cleandriver.dto.customer.CustomerResponse;
import com.cleandriver.dto.customer.CustomerSummary;
import com.cleandriver.model.Customer;
import com.cleandriver.model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CustomerMapper {


    @Mapping(target = "vehicles", source = "vehicles", qualifiedByName = "mapPlateNumbers")
    CustomerResponse toResponse(Customer customer);

    Customer toCustomerFromRequest(CustomerRequest customer);

    CustomerSummary toSummary(Customer customer);

    @Named("mapPlateNumbers")
    default List<String> mapPlateNumbers(List<Vehicle> vehicles) {
        if (vehicles == null) return List.of();
        return vehicles.stream()
                .map(Vehicle::getPlateNumber)
                .toList();
    }


}
