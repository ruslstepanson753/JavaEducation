package com.javarush.stepanov.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.postgresql.util.PGobject;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Map;

@Converter
public class IntegerMapJsonConverter implements AttributeConverter<Map<String, Integer>, PGobject> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public PGobject convertToDatabaseColumn(Map<String, Integer> attribute) {
        try {
            PGobject pgObject = new PGobject();
            pgObject.setType("jsonb");
            pgObject.setValue(attribute == null ? null : objectMapper.writeValueAsString(attribute));
            return pgObject;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка преобразования в JSONB", e);
        }
    }

    @Override
    public Map<String, Integer> convertToEntityAttribute(PGobject dbData) {
        try {
            return dbData == null || dbData.getValue() == null ? null :
                    objectMapper.readValue(dbData.getValue(), new TypeReference<Map<String, Integer>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Ошибка преобразования из JSONB", e);
        }
    }
}