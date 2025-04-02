package com.javarush.stepanov.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;
import java.util.Map;
import org.postgresql.util.PGobject ;
@Converter
public class StringListJsonConverter implements AttributeConverter<Map<String, List<String>>, PGobject> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public PGobject convertToDatabaseColumn(Map<String, List<String>> attribute) {
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
    public Map<String, List<String>> convertToEntityAttribute(PGobject dbData) {
        try {
            return dbData == null || dbData.getValue() == null ? null :
                    objectMapper.readValue(dbData.getValue(), new TypeReference<Map<String, List<String>>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Ошибка преобразования из JSONB", e);
        }
    }
}