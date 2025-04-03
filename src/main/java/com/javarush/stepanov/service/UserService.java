package com.javarush.stepanov.service;

import com.javarush.stepanov.Dto.UserDto;
import com.javarush.stepanov.entity.User;
import com.javarush.stepanov.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final QuestionService questionService;

    @Override
    public String toString() {
        return "DemoService{" +
                "demoRepository=" + userRepository +
                '}';
    }

    public Map.Entry<String, String> getQuestionAnswerEntryById(Long id) {
        User user = getUser(id);
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

    public void answerProcessing(Long userId, String qoodAnswer) {
        User user = getUser(userId);
        if(qoodAnswer.equals("true")){
            doGoodAnswerChanges(user);
        }else {
            doBadAnswerChanges(user);
        }
        userRepository.save(user);
    }

    private void doBadAnswerChanges(User user) {
        String topic = user.getTopic();
        Map<String, Integer> questionPositions = user.getQuestionPositions();
        Map<String, List<String>> questions = user.getQuestions();

        List<String> questionList = questions.get(topic);
        Integer questionPosition = questionPositions.get(topic);
        Integer newQuestionPosition;
        if (questionPosition < questionList.size()){
            newQuestionPosition = questionPosition + 1;
        } else {
            newQuestionPosition = 0;
        }
        questionPositions.put(topic, newQuestionPosition);
    }

    private void doGoodAnswerChanges(User user) {
        String topic = user.getTopic();
        Map<String, Integer> questionPositions = user.getQuestionPositions();
        Map<String, List<String>> questions = user.getQuestions();

        List<String> questionList = questions.get(topic);
        int questionPosition = questionPositions.get(topic).intValue();
        if (questionList.size()>0){
            questionList.remove(questionPosition);
        }
    }

    private User getUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.orElseThrow();
        return user;
    }
}
