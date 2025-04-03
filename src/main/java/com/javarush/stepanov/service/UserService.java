package com.javarush.stepanov.service;

import com.javarush.stepanov.Dto.UserDto;
import com.javarush.stepanov.entity.User;
import com.javarush.stepanov.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final QuestionService questionService;

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

    public Map.Entry<String, String> getQuestionAnswerSetById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.orElseThrow();
        String topic = user.getTopic();
        Map<String, Integer> questionPositions = user.getQuestionPositions();
        Map<String, List<String>> questions = user.getQuestions();

        List<String> questionList = questions.get(topic);
        int questionPosition = questionPositions.get(topic).intValue();
        String question = questionList.get(questionPosition);
        String answer = questionService.getAnswer(question);
        Map.Entry<String, String> result = Map.entry(question, answer);
        return result;
    }
}
