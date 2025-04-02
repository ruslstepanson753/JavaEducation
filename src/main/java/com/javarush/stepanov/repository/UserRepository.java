package com.javarush.stepanov.repository;

import com.javarush.stepanov.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long>{

}
