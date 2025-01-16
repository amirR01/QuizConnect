package com.games.QuizConnect.model.dto.response;

import com.games.QuizConnect.model.entity.Question;
import com.games.QuizConnect.model.enums.QuestionDifficulty;
import lombok.Data;

@Data
public class ViewQuestionResponseDto {
    private Integer id;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private Integer correctOption;
    private QuestionDifficulty difficulty;
    private Integer categoryId;
    private String categoryName;
    private Integer designerId;

    public static ViewQuestionResponseDto fromQuestion(Question question) {
        ViewQuestionResponseDto dto = new ViewQuestionResponseDto();
        dto.setId(question.getId());
        dto.setQuestion(question.getQuestion());
        dto.setOption1(question.getOptions().getByIndex(1));
        dto.setOption2(question.getOptions().getByIndex(2));
        dto.setOption3(question.getOptions().getByIndex(3));
        dto.setOption4(question.getOptions().getByIndex(4));
        dto.setCorrectOption(question.getCorrectOption());
        dto.setDifficulty(question.getDifficulty());
        dto.setCategoryId(question.getCategory().getId());
        dto.setCategoryName(question.getCategory().getName());
        dto.setDesignerId(question.getDesigner().getId());
        return dto;
    }
}
