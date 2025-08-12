package com.controller;

import com.cleandriver.dto.employed.EmployedRequest;
import com.cleandriver.dto.employed.EmployedResponse;
import com.cleandriver.service.interfaces.IEmployedService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employed")
public class EmployedController {
    @Autowired
    private IEmployedService employedService;

    @PostMapping("create")
    public ResponseEntity<EmployedResponse> createEmployed(@RequestBody @Valid
                                                           EmployedRequest employed){
        return ResponseEntity.status(HttpStatus.CREATED).body(employedService.createEmployed(employed));
    }

    @PutMapping("/activate/{employedId}")
    public ResponseEntity<String> setActive(@PathVariable Long employedId,@RequestParam boolean active){
        employedService.activateEmployed(employedId,active);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

}
