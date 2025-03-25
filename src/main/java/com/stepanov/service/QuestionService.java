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
        // Читаем весь контент файла
        String content = new String(Files.readAllBytes(Paths.get(filePath)));

        // Регулярное выражение для поиска заголовков вопросов (начинаются с ## или #)
        Pattern questionPattern = Pattern.compile("(##?\\s*(.+?))\n(.*?)(?=(##|$))",
                Pattern.DOTALL | Pattern.MULTILINE);
        Matcher matcher = questionPattern.matcher(content);

        while (matcher.find()) {
            String question = matcher.group(2).trim();
            String answer = matcher.group(3).trim();

            // Удаляем лишние символы и переводы строк с помощью Pattern.compile()
            answer = answer.replaceAll("\\[к оглавлению\\].*", "")
                    .replaceAll("\\n+", "\n")
                    .trim();

            // Добавляем в Map, очищая от markdown-форматирования
            questionsAndAnswers.put(
                    question.replaceAll("[*_]", ""),
                    answer
            );
        }
        return questionsAndAnswers;
    }

    public static void main(String[] args) {
        try {
            Map<String, String> result = parseDocument("src/main/resources/questions/core.md");

            // Путь для выходного файла
            Path outputPath = Paths.get("src/main/resources/questions/core_processed.txt");

            // Создаем директории, если они не существуют
            Files.createDirectories(outputPath.getParent());

            // Открываем файл для записи (перезаписываем если существует)
            try (BufferedWriter writer = Files.newBufferedWriter(outputPath,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

                // Записываем каждый вопрос и ответ в файл
                for (Map.Entry<String, String> entry : result.entrySet()) {
                    writer.write("### " + entry.getKey() + "\n\n");
                    writer.write(entry.getValue() + "\n\n");
                }

                System.out.println("Результаты успешно записаны в: " + outputPath);
            }

        } catch (IOException e) {
            System.err.println("Ошибка при обработке файла:");
            e.printStackTrace();
        }
    }
}
