package com.javarush.stepanov.repository;

import com.javarush.stepanov.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
@JpaRepository
public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional
    Optional<User> findByLogin(String login);

    @Transactional
    Optional<User> findByNikName(String nikName);
}
