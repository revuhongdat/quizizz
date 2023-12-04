package com.example.quizizz.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CategoryQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
}