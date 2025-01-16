package com.games.QuizConnect.model.dto.request;

import com.games.QuizConnect.model.enums.QuestionDifficulty;
import lombok.Data;

@Data
public class GetQuestionsRequestDTO {

    private Integer categoryId;

    private Integer designerId;

    private QuestionDifficulty difficulty;

}
