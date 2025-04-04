package com.javarush.stepanov.service;

import com.javarush.stepanov.entity.User;
import com.javarush.stepanov.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "users")
public class UserService {

    private final UserRepository userRepository;

    @Override
    public String toString() {
        return "DemoService{" +
                "demoRepository=" + userRepository +
                '}';
    }
    @Transactional
    @Cacheable(key = "#id")
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
    @Transactional
    @Cacheable(key = "#login")
    public User getUserByLogin(String login) {
        User user = userRepository.findByLogin(login).orElseThrow();
        // Кэшируем и по ID через отдельный вызов
        cacheUserById(user);
        return user;
    }

    @CachePut(key = "#user.id")
    public User cacheUserById(User user) {
        return user;
    }

    @CachePut(key = "#user.login")
    public User cacheUserByLogin(User user) {
        return user;
    }

    @Transactional
    @Caching(
            put = {
                    @CachePut(key = "#user.id"),
                    @CachePut(key = "#user.login")
            }
    )
    public User saveUser(User user) {
        System.out.println();
        User savedUser = userRepository.save(user);
        // Явно обновляем кэш по обоим ключам
//        cacheUserById(savedUser);
//        cacheUserByLogin(savedUser);
        return savedUser;
    }



    public String getTopicById(Long id) {
        User user = getUserById(id);
        String topic = user.getTopic();
        return topic;
    }

    public Map<String,Integer> getTopicMap(Long id){
        User user = getUserById(id);
        Map<String,Integer> result = new HashMap<>();
        Map<String,List<String>> questions = user.getQuestions();
        for(Map.Entry<String,List<String>> entry : questions.entrySet()){
            result.put(entry.getKey(), entry.getValue().size());}
        return result;
    }

    public LinkedHashMap<String, Integer> getTopUsersMap(Long id) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(0, pageSize, Sort.by(Sort.Direction.DESC, "goodAnswers"));

        return finAll(pageable).getContent().stream()
                .collect(Collectors.toMap(
                        User::getNikName,
                        User::getGoodAnswers,
                        (oldVal, newVal) -> oldVal,  // обработка дубликатов (если nikName повторяется)
                        LinkedHashMap::new  // сохраняет порядок сортировки
                ));
    }

    private Page<User> finAll(Pageable pageable)
    {
        return userRepository.findAll(pageable);
    }

}
