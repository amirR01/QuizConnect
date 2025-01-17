package com.games.QuizConnect.model.dto.response;

import com.games.QuizConnect.model.entity.Question;
import com.games.QuizConnect.model.enums.QuestionDifficulty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ViewQuestionResponseDTO {
    private Integer id;
    private String question;
    private List<String> options = new ArrayList<>();
    private Integer correctOption;
    private QuestionDifficulty difficulty;
    private Integer categoryId;
    private String categoryName;
    private Integer designerId;

    public static ViewQuestionResponseDTO fromQuestionForDesigner(Question question) {
        ViewQuestionResponseDTO dto = fromQuestion(question);
        dto.setCorrectOption(question.getCorrectOption());

        return dto;
    }

    public static ViewQuestionResponseDTO fromQuestionForPlayer(Question question) {
        return fromQuestion(question);
    }

    private static ViewQuestionResponseDTO fromQuestion(Question question) {
        ViewQuestionResponseDTO dto = new ViewQuestionResponseDTO();
        dto.setId(question.getId());
        dto.setQuestion(question.getQuestion());
        for (int i = 1; i <= 4; i++) {
            dto.getOptions().add(question.getOptions().getByIndex(i));
        }
        dto.setDifficulty(question.getDifficulty());
        dto.setCategoryId(question.getCategory().getId());
        dto.setCategoryName(question.getCategory().getName());
        dto.setDesignerId(question.getDesigner().getId());
        return dto;
    }
}
