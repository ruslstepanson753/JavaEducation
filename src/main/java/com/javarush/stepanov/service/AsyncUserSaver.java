package com.javarush.stepanov.service;

import com.javarush.stepanov.entity.User;
import com.javarush.stepanov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AsyncUserSaver {
    private final UserRepository userRepository;

    @Async
    public void saveAsync(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {

        }
    }
}
