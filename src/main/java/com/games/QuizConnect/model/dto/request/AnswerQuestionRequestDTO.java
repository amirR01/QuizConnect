package com.games.QuizConnect.model.dto.request;

import com.games.QuizConnect.model.BaseRequestDTO;
import lombok.Data;

@Data
public class AnswerQuestionRequestDTO extends BaseRequestDTO {

    private Integer questionId;

    private Integer answer;

    @Override
    public void validate() {
        if (questionId == null) {
            throw new IllegalArgumentException("Question ID cannot be empty");
        }
        if (answer == null) {
            throw new IllegalArgumentException("Answer cannot be empty");
        }

    }
}
