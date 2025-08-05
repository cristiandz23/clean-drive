package com.cleandriver.service.interfaces;

import com.cleandriver.dto.wash.WashRequest;
import com.cleandriver.dto.wash.WashResponse;

public interface IWashService {

    WashResponse initWash(WashRequest washRequest);

    WashResponse endWash(Long washId);

    WashResponse cancelWash(Long washId);

}
