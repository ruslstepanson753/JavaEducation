package com.javarush.stepanov.controllers;

import com.javarush.stepanov.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;
import java.util.SortedMap;

@AllArgsConstructor
@Controller
public class HallOfFameController extends AbstractUserController {
UserService userService;
    @GetMapping(value = "hall-of-fame")
    public String hallOfFame(HttpServletRequest req) {
        Long id = getUserId(req);
        LinkedHashMap<String,Integer> topUsersMap = userService.getTopUsersMap(id);
        req.setAttribute("topUsersMap", topUsersMap);
        return "hall-of-fame";
    }
}
