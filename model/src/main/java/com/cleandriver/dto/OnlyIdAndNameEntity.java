package com.cleandriver.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OnlyIdAndNameEntity(
        @NotNull @Positive
        Long id,
        String name
) {
}
