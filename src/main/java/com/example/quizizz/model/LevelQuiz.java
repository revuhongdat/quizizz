package com.example.quizizz.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class LevelQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
}