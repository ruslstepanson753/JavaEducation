package com.javarush.stepanov.controllers;

import com.javarush.stepanov.entity.User;
import com.javarush.stepanov.service.QuestionService;
import com.javarush.stepanov.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@AllArgsConstructor
@Controller
public class SettingsController extends AbstractUserController {
    QuestionService questionService;
    UserService userService;

    @GetMapping("/settings")
    public String settings(HttpServletRequest request, Model model) {
        Long id = getUserId(request);
        Map<String,Integer> topicSet = userService.getTopicMap(id);
        String currentTopic = userService.getTopicById(id);

        model.addAttribute("topicMap", topicSet);
        model.addAttribute("topic", currentTopic);
        return "settings";
    }

    @PostMapping("/settings")
    public String updateTopic(@RequestParam String topic, HttpServletRequest request) {
        Long userId = getUserId(request);
        updateUserTopic(userId, topic);
        return "redirect:/settings";
    }

    public void updateUserTopic(Long id, String topic) {
        User user = userService.getUserById(id);
        user.setTopic(topic);
        userService.saveUser(user);
    }


}
