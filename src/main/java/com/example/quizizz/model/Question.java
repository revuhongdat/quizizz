package com.example.quizizz.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private int status;

    @ManyToOne
    @JoinColumn(name = "id_question_type")
    private TypeQuestion typeQuestion;

    @ManyToOne
    @JoinColumn(name = "id_category_question")
    private CategoryQuestion categoryQuestion;

    @ManyToOne
    @JoinColumn(name = "id_level_question")
    private LevelQuestion levelQuestion;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
}
