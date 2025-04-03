package com.javarush.stepanov.controllers;

import com.javarush.stepanov.service.QuestionService;
import com.javarush.stepanov.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.AbstractController;

import java.util.Set;

@AllArgsConstructor
@Controller
public class SettingsController extends AbstractUserController {
    QuestionService questionService;
    UserService userService;

    @GetMapping("/settings")
    public String settings(HttpServletRequest request, Model model) {
        Long id = getUserId(request);
        Set<String> topicSet = questionService.getTopicSet();
        String currentTopic = userService.getTopicById(id);

        model.addAttribute("topicSet", topicSet);
        model.addAttribute("topic", currentTopic);
        return "settings";
    }

    @PostMapping("/settings")
    public String updateTopic(@RequestParam String topic, HttpServletRequest request) {
        Long userId = getUserId(request);
        userService.updateUserTopic(userId, topic);
        return "redirect:/settings";
    }
}
