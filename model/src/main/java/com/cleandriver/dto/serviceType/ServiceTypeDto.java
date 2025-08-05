package com.cleandriver.dto.serviceType;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceTypeDto {

    private Long id;

    private String name;

    private String description;

    private int durationInMinutes;

}
