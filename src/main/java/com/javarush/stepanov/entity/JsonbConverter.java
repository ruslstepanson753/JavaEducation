package com.javarush.stepanov.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Map;

@Converter
public class JsonbConverter implements AttributeConverter<Object, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        try {
            return attribute == null ? null : objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка преобразования в JSON", e);
        }
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        try {
            return dbData == null ? null : objectMapper.readValue(dbData, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка преобразования из JSON", e);
        }
    }
}
