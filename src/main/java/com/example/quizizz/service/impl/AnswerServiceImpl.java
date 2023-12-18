package com.example.quizizz.service.impl;

import com.example.quizizz.model.Answer;
import com.example.quizizz.model.Question;
import com.example.quizizz.repository.AnswerRepository;
import com.example.quizizz.service.AnswerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerServiceImpl implements AnswerService {
    final
    AnswerRepository answerRepository;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public Iterable<Answer> findAll() {
        return answerRepository.findAll();
    }

    @Override
    public Optional<Answer> findById(Long id) {
        return answerRepository.findById(id);
    }

    @Override
    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public void delete(Long id) {
        answerRepository.deleteById(id);
    }


    @Override
    public void deleteByContent(String content) {
        answerRepository.deleteByContent(content);
    }

    @Override
    public Optional<Question> findQuestionByAnswerId(Long id) {
        return answerRepository.findQuestionByAnswerId(id);
    }
}
