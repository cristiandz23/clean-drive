package com.cleandriver.dto.employed;

import com.cleandriver.dto.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployedResponse {

    private Long employedId;

    private String name;

    private String lastName;

    private String dni;

    private String phone;

    private AddressDto addressDto;

    private LocalDateTime createdAt;

    private boolean isActive;


}
