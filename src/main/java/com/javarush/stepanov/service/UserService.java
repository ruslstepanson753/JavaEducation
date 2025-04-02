package com.javarush.stepanov.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.stepanov.Dto.UserDto;
import com.javarush.stepanov.entity.User;
import com.javarush.stepanov.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        UserDto userDto = UserDto.fromEntity(user.get());
        return userDto;
    }

    @Override
    public String toString() {
        return "DemoService{" +
                "demoRepository=" + userRepository +
                '}';
    }


}
