package com.example.quizizz.repository;

import com.example.quizizz.model.CategoryQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryQuestionRepository extends JpaRepository<CategoryQuestion, Long> {
    Iterable<CategoryQuestion> findAllByNameContains (String name);
    Iterable<CategoryQuestion> findAllByDescriptionContains (String description);

}
