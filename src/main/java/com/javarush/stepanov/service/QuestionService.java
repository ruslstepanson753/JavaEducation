package com.javarush.stepanov.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuestionService {
    private final static Map<String, Map<String, String>> allAnsersQuestionsMapByTopic = createAllAnswersQuestionsMap();
    private final static Map<String, String> allAnsersQuestions = getAllAnswersQuestions();
    private final static Map<String, List<String>> allAnsersByTopic = getAllAnsersMapByTopic();

    static Map<String, String> parseDocument(Path filePath) throws IOException {
        Map<String, String> questionsAndAnswers = new HashMap<>();
        String content = new String(Files.readAllBytes(filePath));

        Pattern questionPattern = Pattern.compile("^#(##\\s*.*)$", Pattern.MULTILINE);
        Matcher questionMatcher = questionPattern.matcher(content);

        int prevQuestionEnd = 0;
        String prevQuestion = null;
        String prevAnswer = null;

        while (questionMatcher.find()) {
            if (prevQuestion != null) {
                // Извлекаем ответ между предыдущим вопросом и текущим
                prevAnswer = content.substring(prevQuestionEnd, questionMatcher.start()).trim();
                prevAnswer = processAnswer(prevAnswer);
                questionsAndAnswers.put(prevQuestion, prevAnswer);
            }

            String prevQuestionPre = "\t" + questionMatcher.group(1).trim();
            prevQuestion = prevQuestionPre
                    .replace("_", "")
                    .replace("__", "");
            prevQuestionEnd = questionMatcher.end();
        }

        if (prevQuestion != null) {
            prevAnswer = content.substring(prevQuestionEnd).trim();
            prevAnswer = processAnswer(prevAnswer);
            questionsAndAnswers.put(prevQuestion, prevAnswer);
        }

        return questionsAndAnswers;
    }

    private static String processAnswer(String answer) {
        StringBuilder result = new StringBuilder();
        boolean inCodeBlock = false;
        String[] lines = answer.split("\n");

        for (String line : lines) {
            if (line.trim().isEmpty() || line.matches("\\[к оглавлению\\]\\(.*\\)")) {
                continue;
            }

            if (line.trim().startsWith("```")) {
                inCodeBlock = !inCodeBlock;
                result.append(line).append("\n");
                continue;
            }

            if (inCodeBlock) {
                result.append(line).append("\n");
                continue;
            }

            String processedLine = line
                    .replace("+", "-")  // Заменяем + на ...
                    .replace("_", "")      // Удаляем _
                    .replace("__", "")    // Удаляем __
                    .replace("*", "")    // Удаляем __
                    .replace(">", "");    // Удаляем __

            result.append("\t").append(processedLine).append("\n");
        }

        return result.toString().replaceAll("\\s+$", "");
    }

    private static Map<String, Map<String, String>> createAllAnswersQuestionsMap() {
        Map<String, Map<String, String>> result = new HashMap<>();
        String pathInfo = "src/main/resources/questions";
        try (Stream<Path> paths = Files.list(Paths.get(pathInfo))) {
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

    private static Map<String, String> getAllAnswersQuestions() {
        Map<String, String> flatMap = new HashMap<>();
        allAnsersQuestionsMapByTopic.values() // Получаем коллекцию внутренних карт
                .forEach(innerMap -> flatMap.putAll(innerMap)); // Добавляем все их элементы
        return flatMap; // Возвращаем заполненную карту
    }

    private static Map<String, List<String>> getAllAnsersMapByTopic() {
        return allAnsersQuestionsMapByTopic.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey, // Внешний ключ остается тем же
                        entry -> new ArrayList<>(entry.getValue().keySet()) // Преобразуем ключи внутренней карты в List
                ));
    }

    public static String getAnswer(String question) {
        return allAnsersQuestions.get(question);
    }


    public static void main(String[] args) {
        String pathInfo = "src/main/resources/info/";
        allAnsersQuestionsMapByTopic.forEach((topic, questionAnswerMap) -> {
            Path outputPath = Path.of(pathInfo + topic + ".md");

            try (BufferedWriter writer = Files.newBufferedWriter(
                    outputPath,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            )) {
                questionAnswerMap.forEach((question, answer) -> {
                    try {
                        writer.write("## " + question + "\n");
                        writer.write(answer + "\n\n"); // Extra newline for better readability
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to write question: " + question, e);
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException("Failed to create/write file: " + outputPath, e);
            }
        });
    }
}
