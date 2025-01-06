package com.games.QuizConnect.model.dto;

import com.games.QuizConnect.model.enums.QuestionDifficulty;
import lombok.Data;

@Data
public class CreateQuestionRequestDTO {

    private String question;

    private String option1;

    private String option2;

    private String option3;

    private String option4;

    private Integer correctOption;

    private Integer categoryId;

    private QuestionDifficulty difficulty;
}
