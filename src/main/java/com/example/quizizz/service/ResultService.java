package com.example.quizizz.service;

import com.example.quizizz.model.Result;

public interface ResultService extends GeneralService<Result>{
    Iterable<Result> findAllByQuizId(Long id);
}
