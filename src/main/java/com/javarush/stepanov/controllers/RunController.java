package com.javarush.stepanov.controllers;

import com.javarush.stepanov.service.UserService;
import com.javarush.stepanov.util.CookieHelp;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Slf4j
@AllArgsConstructor
@Controller
public class RunController {
    UserService userService;

    @GetMapping("/run")
    public String run(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Long userId = CookieHelp.getUserIdFromCookies(cookies);
        Map.Entry<String,String> questionAnswerSet= userService.getQuestionAnswerSetById(userId);
        request.setAttribute("question", questionAnswerSet.getKey());
        request.setAttribute("answer", questionAnswerSet.getValue());
        return "run";
    }

}
