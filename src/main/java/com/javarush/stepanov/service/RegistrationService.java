package com.javarush.stepanov.service;

import com.javarush.stepanov.Dto.UserDto;
import com.javarush.stepanov.entity.User;
import com.javarush.stepanov.repository.UserRepository;
import com.javarush.stepanov.util.CookieHelp;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.Cookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;
    private final QuestionService questionService;
    @Value("${com.javarush.startTopic}")
    private String startTopic;

    public Cookie register(String login, String password,String nikName) {
        Map<String, List<String>> questions = questionService.getAllAnswersByTopic();
        Map<String,Integer> questionPositions = questionService.getStartQuestionPositions();
        User user = User.builder()
                .login(login)
                .password(password)
                .nikName(nikName)
                .questions(questions)
                .questionPositions(questionPositions)
                .topic(startTopic)
                .build();
        userRepository.save(user);
        Long id = user.getId();
        Cookie cookie = CookieHelp.createCookie(nikName,id);
        return cookie;
    }
}
