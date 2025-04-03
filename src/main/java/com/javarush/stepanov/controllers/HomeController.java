package com.javarush.stepanov.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class HomeController {

    @GetMapping(value = "/home")
    public String home() {
        return "home";
    }

}
