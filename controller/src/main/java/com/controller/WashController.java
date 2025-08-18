package com.controller;


import com.cleandriver.dto.wash.WashRequest;
import com.cleandriver.dto.wash.WashResponse;
import com.cleandriver.persistence.WashRepository;
import com.cleandriver.service.interfaces.IWashService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/wash")
public class WashController {

    @Autowired
    private IWashService washService;

    @PostMapping("init")
    public ResponseEntity<WashResponse> initWash(@RequestBody @Valid
                                                 WashRequest wash){
        return ResponseEntity.status(HttpStatus.CREATED).body(washService.initWash(wash));
    }

    @PostMapping("end-wash")
    public ResponseEntity<WashResponse> endWash(@RequestParam Long washId){
        return ResponseEntity.status(HttpStatus.CREATED).body(washService.endWash(washId));
    }

    @GetMapping("get-current-washes")
    public ResponseEntity<List<WashResponse>> currentWashes(){
        return ResponseEntity.status(HttpStatus.OK).body(washService.getCurrentWashes());
    }

    @GetMapping("get-washes-today")
    public ResponseEntity<List<WashResponse>> getWashesToday(){
        return ResponseEntity.status(HttpStatus.OK).body(washService.getWashToday());
    }

    @GetMapping("get-washes-by-date")
    public ResponseEntity<List<WashResponse>> getWashesByDay(@RequestParam LocalDate date){
        return ResponseEntity.status(HttpStatus.OK).body(washService.getWashByDate(date));
    }

    @GetMapping("get-washes-by-period")
    public ResponseEntity<List<WashResponse>> getWashesByPeriod(@RequestParam LocalDate from,
                                                             @RequestParam LocalDate until){
        return ResponseEntity.status(HttpStatus.OK).body(washService.getWashByDate(from,until));
    }


}
