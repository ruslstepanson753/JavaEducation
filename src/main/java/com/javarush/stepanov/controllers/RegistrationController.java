package com.javarush.stepanov.controllers;

import com.javarush.stepanov.service.RegistrationService;
import com.javarush.stepanov.util.CookieHelp;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
public class RegistrationController {
    RegistrationService registrationService;
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
