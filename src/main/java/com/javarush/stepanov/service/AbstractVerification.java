package com.javarush.stepanov.service;

public abstract class AbstractVerification {
    public boolean fieldIsEmpty(String... fields) {
        for (String field : fields) {
            if (field.isEmpty()) return true;
        }
        return false;
    }
}
