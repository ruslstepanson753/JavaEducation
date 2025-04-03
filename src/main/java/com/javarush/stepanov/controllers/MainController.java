package com.javarush.stepanov.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class MainController {

    @GetMapping(value = "/home")
    public String home(Model model) {
        model.addAttribute("header", "Главная страница");
        return "home"; // Возвращает шаблон home.html из папки templates
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
