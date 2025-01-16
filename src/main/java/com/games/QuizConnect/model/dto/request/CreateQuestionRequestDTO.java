package com.games.QuizConnect.model.dto.request;

import com.games.QuizConnect.model.BaseDTO;
import com.games.QuizConnect.model.entity.Question;
import com.games.QuizConnect.model.enums.QuestionDifficulty;
import lombok.Data;

@Data
public class CreateQuestionRequestDTO extends BaseDTO {

    private String question;

    private String option1;

    private String option2;

    private String option3;

    private String option4;

    private Integer correctOption;

    private Integer categoryId;

    private QuestionDifficulty difficulty;


    @Override
    public void validate() {
        if (question == null || question.isBlank()) {
            throw new IllegalArgumentException("Question cannot be empty");
        }
        if (option1 == null || option1.isBlank()) {
            throw new IllegalArgumentException("Option 1 cannot be empty");
        }
        if (option2 == null || option2.isBlank()) {
            throw new IllegalArgumentException("Option 2 cannot be empty");
        }
        if (option3 == null || option3.isBlank()) {
            throw new IllegalArgumentException("Option 3 cannot be empty");
        }
        if (option4 == null || option4.isBlank()) {
            throw new IllegalArgumentException("Option 4 cannot be empty");
        }
        if (correctOption == null) {
            throw new IllegalArgumentException("Correct option cannot be empty");
        }
        if (difficulty == null) {
            throw new IllegalArgumentException("Difficulty cannot be empty");
        }
    }

    public Question.QuestionOptions getQuestionOptions() {
        return new Question.QuestionOptions(option1, option2, option3, option4);
    }
}
