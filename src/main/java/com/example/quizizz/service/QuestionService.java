package com.example.quizizz.service;

import com.example.quizizz.model.Question;

public interface QuestionService extends GeneralService<Question>{
    Iterable<Question> findAllByCategoryQuestion_Id (Long categoryQuestion_id);
    Iterable<Question> findAllByQuizId (Long quizId);
    Iterable<Question> findAllByContentContains(String content);
    Iterable<Question> findAllByUser_Id (Long id);
}
