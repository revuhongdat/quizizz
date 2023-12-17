package com.example.quizizz.service;

import com.example.quizizz.model.Answer;

public interface AnswerService extends GeneralService<Answer> {
    void deleteByContent(String content);
}
