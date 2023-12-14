package com.example.quizizz.repository;

import com.example.quizizz.model.CategoryQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryQuizRepository extends JpaRepository<CategoryQuiz, Long> {
    Optional<CategoryQuiz> findCategoryQuizByName (String name);
    Iterable<CategoryQuiz> findAllByNameContains (String name);
    Iterable<CategoryQuiz> findAllByDescriptionContains (String description);

}
