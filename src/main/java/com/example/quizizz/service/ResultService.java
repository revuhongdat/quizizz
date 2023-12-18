package com.example.quizizz.service;

import com.example.quizizz.model.Result;

public interface ResultService extends GeneralService<Result>{
    Iterable<Result> findAllByQuizId(Long id);
    Result findAllByUserIdAndQuizIdNewest(Long idUser, Long idQuiz);
    Iterable<Result> findAllByUserId(Long idUser);
}
