package com.example.quizizz.service.impl;

import com.example.quizizz.model.Question;
import com.example.quizizz.repository.QuestionRepository;
import com.example.quizizz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class QuestionServiceImpl implements QuestionService {
    final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Iterable<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void delete(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public Iterable<Question> findAllByCategoryQuestion_Id(Long categoryQuestion_id) {
        return questionRepository.findAllByCategoryQuestion_Id(categoryQuestion_id);
    }

    @Override
    public Iterable<Question> findAllByQuizId(Long quizId) {
        return questionRepository.findQuestionsByQuizId(quizId);
    }

    @Override
    public Iterable<Question> findAllByContentContains(String content) {
        return questionRepository.findAllByContentContains(content);
    }

    @Override
    public Iterable<Question> findAllByUser_Id(Long id) {
        return questionRepository.findAllByUser_Id(id);
    }
}
