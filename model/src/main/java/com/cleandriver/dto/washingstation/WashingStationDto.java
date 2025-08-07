package com.cleandriver.dto.washingstation;


import com.cleandriver.model.Appointment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WashingStationDto {


    private Long id;

    @NotNull
    private String name;

    private List<Appointment> appointments;

    private boolean isBusy;


}
