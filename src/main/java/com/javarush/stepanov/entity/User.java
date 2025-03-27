package com.javarush.stepanov.entity;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private String nikName;

    @Convert(converter = JsonbConverter.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, List<String>> questions;

    @Convert(converter = JsonbConverter.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Integer> questionPositions;

    private String topic;
}
