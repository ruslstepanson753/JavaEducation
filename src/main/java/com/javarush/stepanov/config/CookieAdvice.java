package com.javarush.stepanov.config;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import jakarta.servlet.http.Cookie;
@ControllerAdvice
public class CookieAdvice {

    @ModelAttribute("userNickname")
    public String addUserNickname(HttpServletRequest request) {
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("userData".equals(cookie.getName())) {
                        String decodedJson = new String(
                                Base64.getDecoder().decode(cookie.getValue()),
                                StandardCharsets.UTF_8
                        );
                        JsonNode userData = new ObjectMapper().readTree(decodedJson);
                        if (userData.has("nikName")) {
                            return userData.get("nikName").asText();
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Логирование ошибки
        }
        return "Гость";
    }
}
