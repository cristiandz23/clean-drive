package com.cleandriver.dto.wash;

import com.cleandriver.model.Appointment;
import com.cleandriver.model.Employed;
import com.cleandriver.model.WashingStation;
import com.cleandriver.model.enums.WashStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
//@AllArgsConstructor
@Data
public class WashResponse {

    private Long id;

    private WashStatus status;

    private LocalDateTime initAt;

    private Long appointmentId;

    private String washingStation;

    private String employed;

}
