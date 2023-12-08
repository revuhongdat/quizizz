package com.example.quizizz.service.impl;

import com.example.quizizz.model.Quiz;
import com.example.quizizz.repository.QuizRepository;
import com.example.quizizz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service

public class QuizServiceImpl implements QuizService {
    @Autowired
    QuizRepository quizRepository;
    @Override
    public Iterable<Quiz> findAll() {
        return quizRepository.findAll();
    }

    @Override
    public Optional<Quiz> findById(Long id) {
        return quizRepository.findById(id);
    }

    @Override
    public Quiz save(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public void delete(Long id) {
        quizRepository.deleteById(id);
    }

}
