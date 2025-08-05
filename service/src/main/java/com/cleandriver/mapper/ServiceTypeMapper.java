package com.cleandriver.mapper;


import com.cleandriver.dto.serviceType.ServiceTypeDto;
import com.cleandriver.model.ServiceType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceTypeMapper {

    ServiceType toServiceType(ServiceTypeDto serviceTypeDto);

}
