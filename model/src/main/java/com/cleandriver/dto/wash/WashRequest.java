package com.cleandriver.dto.wash;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WashRequest {

    private Long appointmentId;

    private Long washingStationId;

    private Long employedId;



}
