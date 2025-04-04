package com.javarush.stepanov.service;

import com.javarush.stepanov.entity.User;
import com.javarush.stepanov.repository.UserRepository;
import com.javarush.stepanov.util.CookieHelp;
import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class AutentificationService extends AbstractVerification {

    private final UserRepository userRepository;

    public Cookie autentificate(String login, String passwordFromView) {
        User user = userRepository.findByLogin(login).orElse(null);
        return CookieHelp.createCookie(user.getNikName(), user.getId());
    }

    public boolean loginOrPasswordIsIncorrect(String login, String password) {
        User user = userRepository.findByLogin(login).orElse(null);
        if (user == null) {
            return true;
        }
        return !user.getPassword().equals(password);
    }

    public static class AuthenticationException extends RuntimeException {
        public AuthenticationException(String message) {
            super(message);
        }
    }
}
