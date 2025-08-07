package com.cleandriver.mapper;

import com.cleandriver.dto.AddressDto;
import com.cleandriver.model.Address;
import org.mapstruct.Mapper;

import java.util.Objects;
import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toAddress(AddressDto address);

    AddressDto toAddressDto(Address address);

    // ✅ Agregá este método default para evitar instancias vacías
    default Address toEntityIgnoreEmpty(AddressDto dto) {
        if (dto == null) return null;

        boolean allNull = Stream.of(dto.getStreet(), dto.getLocality(),
                        dto.getNumber(),dto.getProvince(),dto.getPostalCode())
                .allMatch(Objects::isNull);

        if (allNull)
            return null;

        return toAddress(dto);
    }
}
