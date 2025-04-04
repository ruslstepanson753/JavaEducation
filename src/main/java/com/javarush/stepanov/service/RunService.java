package com.javarush.stepanov.service;

import com.javarush.stepanov.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class RunService {
    QuestionService questionService;
    UserService userService;

    public Map.Entry<String, String> getQuestionAnswerEntryById(Long id) {
        User user = userService.getUserById(id);
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

    private Integer questionPositions(Map<String, Integer> question, List<String> questionList,String topic) {
        Integer questionPosition = question.get(topic);
        if (questionList.size()>questionPosition){
            return questionPosition;
        } else {
            return 0;
        }
    }

    public void answerProcessing(Long userId, String qoodAnswer) {
        User user = userService.getUserById(userId);
        Map<String, List<String>> questions = user.getQuestions();
        String topic = user.getTopic();
        List<String> questionList = questions.get(topic);
        if (questionList.size()!=0){
            if(qoodAnswer.equals("true")){
                doGoodAnswerChanges(user);
            }else {
                doBadAnswerChanges(user);
            }
            userService.saveUser(user);
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

}
