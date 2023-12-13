package com.example.quizizz.service;

import com.example.quizizz.model.CategoryQuestion;

public interface CategoryQuestService extends GeneralService<CategoryQuestion>{
    Iterable<CategoryQuestion> findAllByUser_Id(Long id);
}
