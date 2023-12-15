package com.example.quizizz.service.impl;

import com.example.quizizz.model.LevelQuiz;
import com.example.quizizz.repository.LevelQuizRepository;
import com.example.quizizz.service.LevelQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LevelQuizServiceImpl implements LevelQuizService {
    final
    LevelQuizRepository levelQuizRepository;

    @Autowired
    public LevelQuizServiceImpl(LevelQuizRepository levelQuizRepository) {
        this.levelQuizRepository = levelQuizRepository;
    }

    @Override
    public Iterable<LevelQuiz> findAll() {
        return levelQuizRepository.findAll();
    }

    @Override
    public Optional<LevelQuiz> findById(Long id) {
        return levelQuizRepository.findById(id);
    }

    @Override
    public LevelQuiz save(LevelQuiz levelQuiz) {
        return levelQuizRepository.save(levelQuiz);
    }

    @Override
    public void delete(Long id) {
        levelQuizRepository.deleteById(id);
    }
}
