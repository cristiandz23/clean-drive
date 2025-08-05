package com.cleandriver.mapper;

import com.cleandriver.dto.employed.EmployedRequest;
import com.cleandriver.dto.employed.EmployedResponse;
import com.cleandriver.model.Employed;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployedMapper {

    Employed toEmployed(EmployedRequest employedRequest);

    EmployedResponse toEmployedResponse(Employed employed);


}
