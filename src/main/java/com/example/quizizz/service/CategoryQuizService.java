package com.example.quizizz.service;

import com.example.quizizz.model.CategoryQuiz;

import java.util.Optional;

public interface CategoryQuizService extends GeneralService<CategoryQuiz>{
    Optional<CategoryQuiz> findCategoryQuizByName (String name);

}
