package com.javarush.stepanov.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class CookieHelp {

    public static Long getUserIdFromCookie(Cookie cookie) {
        if (cookie != null) {
            if ("userData".equals(cookie.getName())) {
                try {
                    String decodedJson = new String(Base64.getDecoder().decode(cookie.getValue()), StandardCharsets.UTF_8);
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> userData = objectMapper.readValue(decodedJson, new TypeReference<Map<String, Object>>() {
                    });
                    return userData.containsKey("id") ? ((Number) userData.get("id")).longValue() : null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        return null;
    }

    public static Cookie createCookie(String nikName, Long id) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("nikName", nikName);
        userData.put("id", id);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String userDataJson = objectMapper.writeValueAsString(userData);
            String encodedJson = Base64.getEncoder().encodeToString(userDataJson.getBytes(StandardCharsets.UTF_8));
            Cookie userCookie = new Cookie("userData", encodedJson);
            userCookie.setMaxAge(7 * 24 * 60 * 60);
            userCookie.setPath("/");
            userCookie.setSecure(true);
            return userCookie;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to create user cookie", e);
        }
    }
}
