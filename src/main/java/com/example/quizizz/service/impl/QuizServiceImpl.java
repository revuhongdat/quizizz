package com.example.quizizz.service.impl;

import com.example.quizizz.model.Quiz;
import com.example.quizizz.repository.QuizRepository;
import com.example.quizizz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizServiceImpl implements QuizService {
    final
    QuizRepository quizRepository;

    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

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

    @Override
    public Iterable<Quiz> findAllByUserId(Long id) {
        return quizRepository.findAllByUserId(id);
    }

    @Override
    public List<Quiz> findAllByCategoryQuizMostResult(Long id) {
        return quizRepository.findAllByCategoryQuizMostResult(id);
    }

    @Override
    public List<Quiz> findQuizWithMostResultsInEachCategory() {
        return quizRepository.findQuizWithMostResultsInEachCategory();
    }
}
