package com.javarush.stepanov.controllers;

import com.javarush.stepanov.util.CookieHelp;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public abstract class AbstractUserController {
     public Long getUserId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Long userId = CookieHelp.getUserIdFromCookies(cookies);
        return userId;
    }

}
