package com.cleandriver.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UppercaseConverter implements AttributeConverter<String,String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute != null
                ? attribute.replaceAll("\\s+", "").toUpperCase()
                : null;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
