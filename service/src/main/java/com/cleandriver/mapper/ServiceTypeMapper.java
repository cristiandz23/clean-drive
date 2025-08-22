package com.cleandriver.mapper;


import com.cleandriver.dto.OnlyIdAndNameEntity;
import com.cleandriver.dto.serviceType.ServiceTypeDto;
import com.cleandriver.dto.serviceType.ServiceTypeSummary;
import com.cleandriver.model.ServiceType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceTypeMapper {

    ServiceType toServiceType(ServiceTypeDto serviceTypeDto);

    ServiceTypeDto toServiceTypeDto(ServiceType serviceType);

    ServiceTypeSummary toSummary(ServiceType serviceType);

    OnlyIdAndNameEntity onlyIdAndName(ServiceType serviceType);

}
