package com.example.quizizz.service.impl;

import com.example.quizizz.model.Answer;
import com.example.quizizz.repository.AnswerRepository;
import com.example.quizizz.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    AnswerRepository answerRepository;
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
    public Iterable<Answer> findAllByQuestion_Id(Long questionId) {
        return answerRepository.findAllByQuestion_Id(questionId);
    }
}
