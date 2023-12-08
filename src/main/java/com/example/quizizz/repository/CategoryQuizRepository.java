package com.example.quizizz.repository;

import com.example.quizizz.model.CategoryQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryQuizRepository extends JpaRepository<CategoryQuiz, Long> {
}
