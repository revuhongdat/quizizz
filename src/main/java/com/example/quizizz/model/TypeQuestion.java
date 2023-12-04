package com.example.quizizz.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TypeQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}

