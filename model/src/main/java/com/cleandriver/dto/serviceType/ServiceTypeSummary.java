package com.cleandriver.dto.serviceType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServiceTypeSummary {

    private Long id;
    private String name;

    private BigDecimal price;

    private String description;

}
