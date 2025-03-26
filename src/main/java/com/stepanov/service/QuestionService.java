package com.stepanov.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionService {

    public static Map<String, String> parseDocument(String filePath) throws IOException {
        Map<String, String> questionsAndAnswers = new HashMap<>();
        String content = new String(Files.readAllBytes(Paths.get(filePath)));

        Pattern questionPattern = Pattern.compile("^##\\s*(.+)$", Pattern.MULTILINE);
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

            String prevQuestionPre = "\t"+questionMatcher.group(1).trim();
            prevQuestion= prevQuestionPre
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
                    .replace(">", "");    // Удаляем __

            result.append("\t").append(processedLine).append("\n");
        }

        return result.toString().replaceAll("\\s+$", "");
    }

    public static void main(String[] args) {
        try {
            Map<String, String> result = parseDocument("src/main/resources/questions/oop.md");

            Path outputPath = Paths.get("src/main/resources/questions/oop_processed.md");

            Files.createDirectories(outputPath.getParent());

            try (BufferedWriter writer = Files.newBufferedWriter(outputPath,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

                for (Map.Entry<String, String> entry : result.entrySet()) {
                    writer.write("## " + entry.getKey() + "\n");
                    writer.write(entry.getValue() + "\n");
                }

                System.out.println("Результаты успешно записаны в: " + outputPath);
            }

        } catch (IOException e) {
            System.err.println("Ошибка при обработке файла:");
            e.printStackTrace();
        }
    }
}
