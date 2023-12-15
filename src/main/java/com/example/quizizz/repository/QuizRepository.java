package com.example.quizizz.repository;

import com.example.quizizz.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Iterable<Quiz> findAllByUserId(Long id);
}
