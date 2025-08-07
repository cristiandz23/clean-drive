package com.cleandriver.dto.customer;

import com.cleandriver.dto.AddressDto;
import com.cleandriver.dto.vehicle.VehicleRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerRequest {

    private String phone;

    private String name;

    private String lastName;

    @NotNull(message = "D.N.I. cannot be null")
    @NotBlank(message = "D.N.I. cannot have blank spaces")
    private String dni;

    private AddressDto address;

    private List<VehicleRequest> vehicles;


}
