package com.javarush.stepanov.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginForm {
    @NotBlank(message = "Логин обязателен")
    @Size(min = 4, max = 20, message = "Логин должен быть от 4 до 20 символов")
    @Pattern(regexp = "[a-zA-Z0-9_]+", message = "Логин может содержать только буквы, цифры и _")
    private String login;
}
