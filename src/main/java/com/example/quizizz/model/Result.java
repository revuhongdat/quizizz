package com.example.quizizz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalScore;

    private double numberTrue;

    @CreationTimestamp
    private LocalDateTime startTime;

    @UpdateTimestamp
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn (name = "id_quiz")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "id_room")
    private Room room;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "result_answer",
            joinColumns = {@JoinColumn(name = "id_result")},
            inverseJoinColumns = {@JoinColumn(name = "id_answer")})
    private Set<Answer> answers = new HashSet<>();
}
