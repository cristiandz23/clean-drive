package com.cleandriver.dto.employed;

import com.cleandriver.dto.AddressDto;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    private boolean isActive;


}
