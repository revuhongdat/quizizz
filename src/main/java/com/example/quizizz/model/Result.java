package com.example.quizizz.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalScore;

    private int numberTrue;

    @CreationTimestamp
    private LocalDateTime startTime;

    @UpdateTimestamp
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_quiz")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "id_room")
    private Room room;
}
