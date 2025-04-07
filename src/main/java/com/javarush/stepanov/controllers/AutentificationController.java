package com.javarush.stepanov.controllers;

import com.javarush.stepanov.service.AutentificationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
public class AutentificationController {
    AutentificationService autentificationService;

    @PostMapping("autentification")
    public String autentification(@RequestParam String login,
                                  @RequestParam String password,
                                  HttpServletRequest request,
                                  HttpServletResponse response)  {

        if ((autentificationService.fieldIsEmpty(login,password))) {
            request.setAttribute("error","Есть незаполненные поля");
            return "autentification";
        } else if (autentificationService.loginOrPasswordIsIncorrect(login, password)) {
            request.setAttribute("error","Неверный логин или пароль");
            return "autentification";
        }
        Cookie cookie = autentificationService.autentificate(login,password);
        response.addCookie(cookie);
        return "redirect:home";
    }

    @GetMapping("/")
    public String showAutentificationPage(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return "autentification";
        }
        Cookie userCookie = null;
        for (Cookie cookie : cookies) {
            if ("userData".equals(cookie.getName())) {
                userCookie = cookie;
                break;
            }
        }
        if (userCookie == null || userCookie.getValue() == null || userCookie.getValue().isEmpty()) {
            return "autentification";
        }
        return "home";
    }
}
