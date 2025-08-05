package com.cleandriver.dto.employed;


import com.cleandriver.dto.AddressDto;

import java.time.LocalDateTime;

public class EmployedRequest {

    private String name;

    private String lastName;

    private String dni;

    private String phone;

    private AddressDto addressDto;

    private LocalDateTime createdAt;

}
