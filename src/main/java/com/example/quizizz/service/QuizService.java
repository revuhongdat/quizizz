package com.example.quizizz.service;

import com.example.quizizz.model.Quiz;

import java.util.Optional;

public interface QuizService extends GeneralService<Quiz>{
    Iterable<Quiz> findAllByUserId(Long id);
}
