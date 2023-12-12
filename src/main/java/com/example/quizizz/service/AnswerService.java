package com.example.quizizz.service;

import com.example.quizizz.model.Answer;

public interface AnswerService extends GeneralService<Answer> {
    Iterable<Answer> findAllByQuestion_Id (Long questionId);

}
