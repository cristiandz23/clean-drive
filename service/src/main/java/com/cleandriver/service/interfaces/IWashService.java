package com.cleandriver.service.interfaces;

import com.cleandriver.dto.wash.WashRequest;
import com.cleandriver.dto.wash.WashResponse;

import java.time.LocalDate;
import java.util.List;

public interface IWashService {

    WashResponse initWash(WashRequest washRequest);

    WashResponse endWash(Long washId);

    WashResponse cancelWash(Long washId);

    List<WashResponse> getCurrentWashes();

    List<WashResponse> getWashToday();

    List<WashResponse> getWashByDate(LocalDate initAt);

    List<WashResponse> getWashByDate(LocalDate from, LocalDate until);


}
