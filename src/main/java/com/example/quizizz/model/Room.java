package com.example.quizizz.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private Set<User> users;

    @OneToOne
    private Quiz quiz;

    private LocalDateTime timeStart;

    private LocalDateTime timeExam;
}
