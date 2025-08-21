package com.cleandriver.config;

import com.cleandriver.model.enums.VehicleType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Converter
public class DaysOfWeekConverter implements AttributeConverter<List<DayOfWeek>, String> {

    private static final String SPLIT_CHAR = ",";


    @Override
    public String convertToDatabaseColumn(List<DayOfWeek> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        return attribute.stream()
                .map(Enum::name)
                .collect(Collectors.joining(SPLIT_CHAR));
    }

    @Override
    public List<DayOfWeek> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(dbData.split(SPLIT_CHAR))
                .map(DayOfWeek::valueOf)
                .collect(Collectors.toList());
    }
}
