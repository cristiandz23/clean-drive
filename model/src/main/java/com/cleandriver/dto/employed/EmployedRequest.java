package com.cleandriver.dto.employed;


import com.cleandriver.dto.AddressDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployedRequest {
    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @NotNull @NotBlank
    private String dni;

    private String phone;

    private AddressDto addressDto;


}
