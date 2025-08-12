package com.cleandriver.dto.wash;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WashRequest {

    @NotNull @Positive
    private Long appointmentId;

    @NotNull @Positive
    private Long washingStationId;

    @NotNull @Positive
    private Long employedId;



}
