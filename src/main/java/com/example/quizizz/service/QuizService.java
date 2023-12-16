package com.example.quizizz.service;

import com.example.quizizz.model.Quiz;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuizService extends GeneralService<Quiz>{
    Iterable<Quiz> findAllByUserId(Long id);
    List<Quiz> findAllByCategoryQuizMostResult(Long id);
    List<Quiz> findQuizWithMostResultsInEachCategory();
}
