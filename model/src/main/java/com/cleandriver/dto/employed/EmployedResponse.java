package com.cleandriver.dto.employed;

import com.cleandriver.dto.AddressDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployedResponse {

    private Long id;

    private String name;

    private String lastName;

    private String dni;

    private String phone;

    private AddressDto addressDto;

    private LocalDate createdAt;

    private boolean isActive;


}
