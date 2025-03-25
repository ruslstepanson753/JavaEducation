package com.stepanov;

import com.stepanov.service.QuestionService;

import java.io.IOException;
import java.util.Map;

public class RunApp {
    public static void main(String[] args) throws IOException {

        String filePath = "src/main/resources/questions/core.md";
        Map<String,String> map = QuestionService.parseDocument(filePath);
        System.out.println();
    }
}
