package com.example.quizizz.service.impl;

import com.example.quizizz.model.LevelQuestion;
import com.example.quizizz.repository.LevelQuestionRepository;
import com.example.quizizz.service.LevelQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LevelQuestionServiceImpl implements LevelQuestionService {
    final LevelQuestionRepository levelQuestionRepository;

    @Autowired
    public LevelQuestionServiceImpl(LevelQuestionRepository levelQuestionRepository) {
        this.levelQuestionRepository = levelQuestionRepository;
    }

    @Override
    public Iterable<LevelQuestion> findAll() {
        return levelQuestionRepository.findAll();
    }

    @Override
    public Optional<LevelQuestion> findById(Long id) {
        return levelQuestionRepository.findById(id);
    }

    @Override
    public LevelQuestion save(LevelQuestion levelQuestion) {
        return levelQuestionRepository.save(levelQuestion);
    }

    @Override
    public void delete(Long id) {
        levelQuestionRepository.deleteById(id);
    }
}
