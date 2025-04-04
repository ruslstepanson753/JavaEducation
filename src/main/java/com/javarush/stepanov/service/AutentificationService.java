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

    private final UserService userService;

    public Cookie autentificate(String login, String passwordFromView) {
        User user = userService.getUserByLogin(login);
        return CookieHelp.createCookie(user.getNikName(), user.getId());
    }

    public boolean loginOrPasswordIsIncorrect(String login, String password) {
        User user = userService.getUserByLogin(login);
        if (user == null) {
            return true;
        }
        return !user.getPassword().equals(password);
    }

}
