package com.example.quizizz.service;

import com.example.quizizz.model.Answer;
import com.example.quizizz.model.Question;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AnswerService extends GeneralService<Answer> {
    void deleteByContent(String content);
    Optional<Question> findQuestionByAnswerId(Long id);
}
