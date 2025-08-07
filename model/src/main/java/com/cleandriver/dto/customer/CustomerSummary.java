package com.cleandriver.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerSummary {

    private String name;
    private String lastName;
    private String dni;
    private String phone;

}
