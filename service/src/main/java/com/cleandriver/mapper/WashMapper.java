package com.cleandriver.mapper;

import com.cleandriver.dto.wash.WashResponse;
import com.cleandriver.model.Wash;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WashMapper {

    WashResponse toResponse(Wash wash);

}
