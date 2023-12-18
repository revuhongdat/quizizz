package com.example.quizizz.service;

import com.example.quizizz.model.Result;

public interface ResultService extends GeneralService<Result>{
    Iterable<Result> findAllByQuizId(Long id);
    Iterable<Result> findAllByUserIdAndQuizId(Long idUser, Long idQuiz);
    Iterable<Result> findAllByUserId(Long idUser);

    Result findByUserIdAndQuizIdAndNewest(Long idUser, Long idQuiz);
}
