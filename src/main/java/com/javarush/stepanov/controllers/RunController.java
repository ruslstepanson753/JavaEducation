package com.javarush.stepanov.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class RunController {

    @GetMapping(value = "/run")
    public String run(HttpServletRequest request) {

        return "run"; // Возвращает шаблон home.html из папки templates
    }

}
