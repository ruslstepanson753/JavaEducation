package com.javarush.stepanov.service;

import com.javarush.stepanov.entity.User;
import com.javarush.stepanov.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.Cookie;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AutentificationService {
    UserRepository userRepository;
    public Cookie autentificate(String login, String passwordFromView) {
        Optional<User> userOptional = userRepository.findByLogin(login);
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
        String password = user.getPassword();
        if (!password.equals(passwordFromView)) {
            throw new RuntimeException("Wrong password");
        }
        Cookie cookie = new Cookie(user.getLogin(), password);
        return cookie;
    }
}
