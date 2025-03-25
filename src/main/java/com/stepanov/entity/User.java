package com.stepanov.entity;

import java.util.List;
import java.util.Map;

public class User {
    private Long id;
    private String login;
    private String password;
    private String nikName;
    private Map<String, List<String>> questions;
    private Map<String,Integer> questionPositions;
    private String topic;

}
