package com.javarush.stepanov.controllers;

import com.javarush.stepanov.Dto.UserDto;
import com.javarush.stepanov.service.AutentificationService;
import com.javarush.stepanov.service.RegistrationService;
import com.javarush.stepanov.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String autentification(@RequestParam String login,@RequestParam String password, Model model) {
        System.out.println(login+password);
        return "redirect:/home";
    }

    @GetMapping("/")
    public String showLoginPage(Model model) {
        return "autentification";
    }

    @GetMapping("/registration")
    public String showRegistrationPage() {
        return "registration"; // предполагается, что у вас есть registration.html
    }

    @PostMapping("/registration")
    public String registration(@RequestParam String login,@RequestParam String password,@RequestParam String nikName, Model model) {
        UserDto userDto = registrationService.register(login, password, nikName);
        System.out.println(userDto);
        return "redirect:/home";
    }

}
