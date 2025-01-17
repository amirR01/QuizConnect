package com.games.QuizConnect.model.dto.request;

import com.games.QuizConnect.model.BaseRequestDTO;
import com.games.QuizConnect.model.entity.Question;
import com.games.QuizConnect.model.enums.QuestionDifficulty;
import lombok.Data;

import java.util.List;

@Data
public class CreateQuestionRequestRequestDTO extends BaseRequestDTO {

    private String question;

    private List<String> options;

    private Integer correctOption;

    private Integer categoryId;

    private QuestionDifficulty difficulty;


    public void validate() {
        if (question == null || question.isBlank()) {
            throw new IllegalArgumentException("Question cannot be empty");
        }
        if (options == null || options.size() != 4) {
            throw new IllegalArgumentException("Options cannot be empty and must be 4");
        }
        for (String option : options) {
            if (option.isBlank()) {
                throw new IllegalArgumentException("Option cannot be empty");
            }
        }
        if (correctOption == null) {
            throw new IllegalArgumentException("Correct option cannot be empty");
        }
        if (difficulty == null) {
            throw new IllegalArgumentException("Difficulty cannot be empty");
        }
    }

    public Question.QuestionOptions getQuestionOptions() {
        return new Question.QuestionOptions(options.get(0), options.get(1), options.get(2), options.get(3));
    }
}
