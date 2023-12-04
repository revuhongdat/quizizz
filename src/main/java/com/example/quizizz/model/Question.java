package com.example.quizizz.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private int status;

    @ManyToOne
    @JoinColumn(name = "id_quiz")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "id_questionType")
    private TypeQuestion typeQuestion;

    @ManyToOne
    @JoinColumn(name = "id_categoryQuestion")
    private CategoryQuestion categoryQuestion;

    @ManyToOne
    @JoinColumn(name = "id_levelQuestion")
    private LevelQuestion levelQuestion;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
}
