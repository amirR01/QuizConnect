package com.games.QuizConnect.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.games.QuizConnect.model.entity.Question;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public
class QuestionOptionsConverter implements jakarta.persistence.AttributeConverter<Question.QuestionOptions, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Question.QuestionOptions options) {
        try {
            return objectMapper.writeValueAsString(options);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting QuestionOptions to JSON", e);
        }
    }

    @Override
    public Question.QuestionOptions convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, Question.QuestionOptions.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting JSON to QuestionOptions", e);
        }
    }
}