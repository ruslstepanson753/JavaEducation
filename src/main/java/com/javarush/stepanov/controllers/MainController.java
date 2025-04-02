package com.javarush.stepanov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MainController {

    @GetMapping(value = {"/", "/home"})
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

}
