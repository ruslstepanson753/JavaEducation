package com.javarush.stepanov.service;

import com.javarush.stepanov.entity.User;
import com.javarush.stepanov.repository.UserRepository;
import lombok.AllArgsConstructor;
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
        Map<String, List<String>> questions = user.getQuestions();
        String topic = user.getTopic();
        List<String> questionList = questions.get(topic);
        if (questionList.size()!=0){
            Map<String, Integer> questionPositions = user.getQuestionPositions();
            int questionPosition = questionPositions( questionPositions, questionList, topic).intValue();
            String question = questionList.get(questionPosition);
            String answer = questionService.getAnswer(question);
            Map.Entry<String, String> result = Map.entry(question, answer);
            return result;
        } else {
            return Map.entry("по установленной теме все вопросы проработаны", "");
        }
    }

    public void answerProcessing(Long userId, String qoodAnswer) {
        User user = getUser(userId);
        Map<String, List<String>> questions = user.getQuestions();
        String topic = user.getTopic();
        List<String> questionList = questions.get(topic);
        if (questionList.size()!=0){
            if(qoodAnswer.equals("true")){
                doGoodAnswerChanges(user);
            }else {
                doBadAnswerChanges(user);
            }
            userRepository.save(user);
        }else {
            return;
        }
    }

    private void doBadAnswerChanges(User user) {
        String topic = user.getTopic();
        Map<String, Integer> questionPositions = user.getQuestionPositions();
        Map<String, List<String>> questions = user.getQuestions();

        List<String> questionList = questions.get(topic);
        Integer questionPosition = questionPositions(questionPositions,questionList,topic);
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
        Map<String, List<String>> questions = user.getQuestions();
        List<String> questionList = questions.get(topic);
        Map<String, Integer> questionPositions = user.getQuestionPositions();
        int goodAnswers = user.getGoodAnswers();
        goodAnswers++;
        user.setGoodAnswers(goodAnswers);
        int questionPosition = questionPositions(questionPositions,questionList,topic).intValue();
        if (questionList.size()>0){
            questionList.remove(questionPosition);
        }
    }

    private Integer questionPositions(Map<String, Integer> question, List<String> questionList,String topic) {
        Integer questionPosition = question.get(topic);
        if (questionList.size()>questionPosition){
            return questionPosition;
        } else {
            return 0;
        }
    }

    private User getUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.orElseThrow();
        return user;
    }

    public String getTopicById(Long id) {
        User user = getUser(id);
        String topic = user.getTopic();
        return topic;
    }

    public void updateUserTopic(Long id, String topic) {
        User user = getUser(id);
        user.setTopic(topic);
        userRepository.save(user);
    }

    public Map<String,Integer> getTopicMap(Long id){
        User user = getUser(id);
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
