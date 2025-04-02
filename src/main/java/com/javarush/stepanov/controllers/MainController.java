package com.javarush.stepanov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("header", "Главная страница");
        return "home";
    }
}
