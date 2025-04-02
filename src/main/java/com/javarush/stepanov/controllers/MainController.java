package com.javarush.stepanov.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.stepanov.service.AutentificationService;
import com.javarush.stepanov.service.RegistrationService;
import com.javarush.stepanov.service.UserService;
import com.javarush.stepanov.util.CookieHelp;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@AllArgsConstructor
@Controller
public class MainController {
    UserService userService;
    RegistrationService registrationService;
    AutentificationService autentificationService;

    @GetMapping(value = "/home")
    public String home(Model model) {
        model.addAttribute("header", "Главная страница");
        return "home"; // Возвращает шаблон home.html из папки templates
    }

    @GetMapping(value = "/run")
    public String run(Model model) {
        model.addAttribute("header", "Главная страница");
        return "run"; // Возвращает шаблон home.html из папки templates
    }

    @GetMapping(value = "/settings")
    public String settings(Model model) {
        model.addAttribute("header", "Главная страница");
        return "settings"; // Возвращает шаблон home.html из папки templates
    }

    @GetMapping(value = "/statistic")
    public String statistic(Model model) {
        model.addAttribute("header", "Главная страница");
        return "statistic"; // Возвращает шаблон home.html из папки templates
    }

    @GetMapping(value = "/hall-of-fame")
    public String hallOfFame(Model model) {
        model.addAttribute("header", "Главная страница");
        return "hall-of-fame"; // Возвращает шаблон home.html из папки templates
    }

    @GetMapping(value = "/exit")
    public String exit(Model model) {
        model.addAttribute("header", "Главная страница");
        return "exit"; // Возвращает шаблон home.html из папки templates
    }

    @PostMapping("/autentification")
    public String autentification(@RequestParam String login,
                                  @RequestParam String password,
                                  HttpServletRequest request,
                                  HttpServletResponse response)  {
        Cookie cookie = autentificationService.autentificate(login,password);
        response.addCookie(cookie);
        return "redirect:/home";
    }

    @GetMapping("/")
    public String showAutentificationPage(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "autentification";
        }else {
            return "home";
        }

    }

    @GetMapping("/registration")
    public String showRegistrationPage() {
        return "registration"; // предполагается, что у вас есть registration.html
    }

    @PostMapping("/registration")
    public String registration(@RequestParam String login, @RequestParam String password, @RequestParam String nikName, HttpServletResponse response) {
        Cookie cookie = registrationService.register(login, password, nikName);
        response.addCookie(cookie);
        Long id = CookieHelp.getUserIdFromCookie(cookie);
        System.out.println(id);
        return "redirect:/home";
    }



}
