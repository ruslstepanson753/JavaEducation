package com.javarush.stepanov.repository;

import com.javarush.stepanov.config.SessionCreator;
import com.javarush.stepanov.entity.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
@Transactional
public class UserRepository {
    private final SessionCreator sessionCreator;
    public Optional<User> getByIdFromRepo(Long id) {
        Session session = sessionCreator.getSession();
        User user = session.get(User.class, id);
        return Optional.of(user);
    }
}
