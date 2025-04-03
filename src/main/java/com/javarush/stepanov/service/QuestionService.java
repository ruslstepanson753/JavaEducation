package com.javarush.stepanov.service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class QuestionService {
    private final Map<String, Map<String, String>> allAnswersQuestionsMapByTopic;
    private final Map<String, String> allAnswersQuestions;
    private final   Map<String, List<String>> allAnswersByTopic;
    private final   String questionsPath ;
    private final  Integer startAnswerPosition;

    public QuestionService(@Value("${com.javarush.questionsPath}") String questionsPath,
                           @Value("${com.javarush.startAnswerPosition}") int startAnswerPosition) {
        this.questionsPath = questionsPath;
        this.startAnswerPosition = Integer.valueOf(startAnswerPosition);
        allAnswersQuestionsMapByTopic = createAllAnswersQuestionsMapByTopic();
        allAnswersByTopic = createAllAnsersMapByTopic();
        allAnswersQuestions = createAllAnswersQuestions();
    }

    private Map<String, String> parseDocument(Path filePath) throws IOException {
        Map<String, String> questionsAndAnswers = new HashMap<>();
        String content = new String(Files.readAllBytes(filePath));

        Pattern questionPattern = Pattern.compile("^##\\s*(.+)$", Pattern.MULTILINE);
        Matcher questionMatcher = questionPattern.matcher(content);

        int prevQuestionEnd = 0;
        String prevQuestion = null;

        while (questionMatcher.find()) {
            if (prevQuestion != null) {
                String answer = content.substring(prevQuestionEnd, questionMatcher.start());
                // Сохраняем все пробельные символы и преобразуем в HTML-формат
                questionsAndAnswers.put(prevQuestion, formatTextWithIndentation(answer));
            }

            prevQuestion = "\t" + questionMatcher.group(1).trim();
            prevQuestionEnd = questionMatcher.end();
        }

        if (prevQuestion != null) {
            String answer = content.substring(prevQuestionEnd);
            questionsAndAnswers.put(prevQuestion, formatTextWithIndentation(answer));
        }

        return questionsAndAnswers;
    }

    private String formatTextWithIndentation(String text) {
        String withTabs = text.replace("\t", "&emsp;&emsp;");
        return withTabs.replace("\n", "<br>");
    }

    private Map<String, Map<String, String>> createAllAnswersQuestionsMapByTopic() {
        Map<String, Map<String, String>> result = new HashMap<>();

        try (Stream<Path> paths = Files.list(Paths.get(questionsPath))) {
            paths.filter(Files::isRegularFile)
                    .forEach(filePath -> {
                        String fileName = filePath.getFileName().toString();
                        String baseName = fileName.replaceFirst("[.][^.]+$", "");
                        Map<String, String> questionsAndAnswers = new HashMap<>();
                        try {
                            questionsAndAnswers = parseDocument(filePath);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        result.put(baseName, questionsAndAnswers);
                    });
        } catch (IOException e) {
            System.err.println("Ошибка при чтении директории: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Ошибка при чтении директории: " + e.getMessage());
        }
        return result;
    }

    private Map<String, String> createAllAnswersQuestions() {
        Map<String, String> flatMap = new HashMap<>();
        allAnswersQuestionsMapByTopic.values() // Получаем коллекцию внутренних карт
                .forEach(innerMap -> flatMap.putAll(innerMap)); // Добавляем все их элементы
        return flatMap; // Возвращаем заполненную карту
    }

    private Map<String, List<String>> createAllAnsersMapByTopic() {
        return allAnswersQuestionsMapByTopic.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey, // Внешний ключ остается тем же
                        entry -> new ArrayList<>(entry.getValue().keySet()) // Преобразуем ключи внутренней карты в List
                ));
    }

    public String getAnswer(String question) {
        return allAnswersQuestions.get(question);
    }

    public Map<String, Integer> getStartQuestionPositions() {
        Map<String, Integer> result = new HashMap<>();
        allAnswersQuestionsMapByTopic.keySet().stream().forEach(a->result.put(a, startAnswerPosition));
        return result;
    }

    public Map<String, List<String>> getAllAnswersByTopic() {
        Map<String, List<String>>  result = new HashMap<>(allAnswersByTopic);
        return result;
    }

    public Set<String> getTopicSet(){
        return allAnswersByTopic.keySet();
    }
}
