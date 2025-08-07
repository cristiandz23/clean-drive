package com.cleandriver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressDto {


    private String street;

    private String number;

    private String postalCode;

    private String locality;

    private String province;



}
