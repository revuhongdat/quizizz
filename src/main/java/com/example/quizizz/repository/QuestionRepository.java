package com.example.quizizz.repository;

import com.example.quizizz.model.CategoryQuestion;
import com.example.quizizz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Iterable<Question> findAllByCategoryQuestion_Id (Long categoryQuestion_id);
    Iterable<Question> findAllByQuizId (Long quizId);

}
