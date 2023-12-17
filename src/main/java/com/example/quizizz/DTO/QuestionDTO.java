package com.example.quizizz.DTO;

import com.example.quizizz.model.Answer;
import com.example.quizizz.model.Question;

import java.util.Set;

public class QuestionDTO {

    private Question question;
    private Set<Answer> answers;

    public QuestionDTO(Question question, Set<Answer> answers) {
        this.question = question;
        this.answers = answers;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }
}
