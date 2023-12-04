package com.example.quizizz.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String img;
    private int status;

    @ManyToOne
    @JoinColumn(name = "id_question")
    private Question question;
}
