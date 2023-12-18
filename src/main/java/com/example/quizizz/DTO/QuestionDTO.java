package com.example.quizizz.DTO;

import com.example.quizizz.model.Answer;
import com.example.quizizz.model.Question;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class QuestionDTO {

    private Question question;
    private Set<Answer> answers;

    public QuestionDTO(Question question, Set<Answer> answers) {
        this.question = question;
        this.answers = answers;
    }
}
