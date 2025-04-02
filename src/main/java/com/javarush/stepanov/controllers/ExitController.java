package com.javarush.stepanov.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class ExitController {

    @GetMapping("/exit")
    public String exit(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userData".equals(cookie.getName())) {
                    Cookie deleteCookie = new Cookie("userData", "");
                    deleteCookie.setMaxAge(0);
                    deleteCookie.setPath("/");
                    response.addCookie(deleteCookie);
                    break;
                }
            }
        }
        request.getSession().invalidate();
        return "redirect:/";
    }
}
