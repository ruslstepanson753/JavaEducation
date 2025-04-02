package com.javarush.stepanov.service;

import com.javarush.stepanov.entity.User;
import com.javarush.stepanov.repository.UserRepository;
import com.javarush.stepanov.util.CookieHelp;
import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.Cookie;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class AutentificationService {
    UserRepository userRepository;
    public Cookie autentificate(String login, String passwordFromView) {
        Optional<User> userOptional = userRepository.findByLogin(login);
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
        String password = user.getPassword();
        if (!password.equals(passwordFromView)) {
            throw new RuntimeException("Wrong password");
        }
        String nikName = user.getNikName();
        Long id = user.getId();
        Cookie cookie = CookieHelp.createCookie(nikName,id);
        return cookie;
    }
}
