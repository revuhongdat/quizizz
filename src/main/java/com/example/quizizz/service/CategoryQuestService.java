package com.example.quizizz.service;

import com.example.quizizz.model.CategoryQuestion;

public interface CategoryQuestService extends GeneralService<CategoryQuestion>{
    Iterable<CategoryQuestion> findAllByUser_Id(Long id);
    Iterable<CategoryQuestion> findAllByNameContains (String name);
    Iterable<CategoryQuestion> findAllByDescriptionContains (String description);
}
