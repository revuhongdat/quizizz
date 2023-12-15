package com.example.quizizz.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @Column(unique = true, nullable = false)
    private int time;

    @CreationTimestamp
    private LocalDateTime timeCreate;

    private String description;

    @Column(nullable = false)
    private double passScore;

    @Column(nullable = false)
    private int status;

    @ManyToOne
    @JoinColumn(name = "id_category_quiz")
    private CategoryQuiz categoryQuiz;

    @ManyToOne
    @JoinColumn(name = "id_level_quiz")
    private LevelQuiz levelQuiz;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "quiz_question",
            joinColumns = {@JoinColumn(name = "id_quiz")},
            inverseJoinColumns = {@JoinColumn(name = "id_question")})
    private Set<Question> questions = new HashSet<>();
}
