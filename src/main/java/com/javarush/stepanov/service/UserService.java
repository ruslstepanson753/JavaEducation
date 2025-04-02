package com.javarush.stepanov.service;

import com.javarush.stepanov.entity.User;
import com.javarush.stepanov.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow();
    }

    @Override
    public String toString() {
        return "DemoService{" +
                "demoRepository=" + userRepository +
                '}';
    }
}
