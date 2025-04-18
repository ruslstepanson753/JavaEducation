package com.javarush.stepanov.controllers;

import com.javarush.stepanov.service.RunService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Slf4j
@AllArgsConstructor
@Controller
public class RunController extends AbstractUserController{
    RunService runService;

    @GetMapping("/run")
    public String run(HttpServletRequest request) {
        Long userId = getUserId(request);
        Map.Entry<String,String> questionAnswerSet= runService.getQuestionAnswerEntryById(userId);
        request.setAttribute("question", questionAnswerSet.getKey());
        request.setAttribute("answer", questionAnswerSet.getValue());
        return "run";
    }

    @PostMapping("/run")
    public String runPost(HttpServletRequest request) {
        Long userId = getUserId(request);
        String qoodAnswer = request.getParameter("good-answer");
        runService.answerProcessing(userId,qoodAnswer);
        return "redirect:/run";
    }



}
