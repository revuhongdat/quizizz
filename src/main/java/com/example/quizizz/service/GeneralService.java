package com.example.quizizz.service;

import com.example.quizizz.model.Quiz;

import java.util.Optional;

public interface GeneralService<T> {
    Iterable<T> findAll();
    Optional<T> findById(Long id);
    T save (T t);
    void delete(Long id);

}
