package com.example.quizizz.repository;

import com.example.quizizz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Iterable<Question> findAllByCategoryQuestion_Id(Long categoryQuestion_id);

    @Modifying
    @Query(value = "DELETE FROM question WHERE :id NOT IN (SELECT id_question FROM quiz_question)", nativeQuery = true)
    @Transactional
    void deleteQuestionNotInQuiz(@Param("id") Long id);

    Iterable<Question> findAllByContentContains(String content);
    @Query(value = "SELECT * FROM question q WHERE q.id IN (SELECT qq.id_question FROM quiz_question qq WHERE qq.id_quiz = :id)", nativeQuery = true)
    List<Question> findQuestionsByQuizId(@Param("id") Long id);

    Iterable<Question> findAllByUser_Id(Long id);
}
