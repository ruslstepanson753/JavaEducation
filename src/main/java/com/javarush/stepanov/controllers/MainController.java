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

    @GetMapping(value = "/home")
    public String home(Model model) {
        model.addAttribute("header", "Главная страница");
        return "home"; // Возвращает шаблон home.html из папки templates
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

}
