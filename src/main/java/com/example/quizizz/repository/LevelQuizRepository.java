package com.example.quizizz.repository;

import com.example.quizizz.model.LevelQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelQuizRepository extends JpaRepository<LevelQuiz, Long> {
}
