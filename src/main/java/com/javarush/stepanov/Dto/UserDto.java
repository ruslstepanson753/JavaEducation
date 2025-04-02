package com.javarush.stepanov.Dto;

import com.javarush.stepanov.entity.User;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto {
    private Long id;
    private String login;
    private String nikName;
    private Map<String, List<String>> questions;
    private Map<String, Integer> questionPositions;
    private String topic;

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .nikName(user.getNikName())
                .questions(user.getQuestions())
                .questionPositions(user.getQuestionPositions())
                .topic(user.getTopic())
                .build();
    }

    public static User toEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .login(userDto.getLogin())
                .nikName(userDto.getNikName())
                .questions(userDto.getQuestions())
                .questionPositions(userDto.getQuestionPositions())
                .topic(userDto.getTopic())
                .build();
    }
}