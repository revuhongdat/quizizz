package com.example.quizizz.repository;

import com.example.quizizz.model.Answer;
import com.example.quizizz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    void deleteByContent(String content);
    @Query("SELECT q FROM Question q JOIN q.answers a WHERE a.id = :id")
    Optional<Question> findQuestionByAnswerId(@Param("id") Long id);
}