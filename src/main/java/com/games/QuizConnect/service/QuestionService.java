package com.games.QuizConnect.service;

import com.games.QuizConnect.model.entity.Question;
import com.games.QuizConnect.model.enums.QuestionDifficulty;

public interface QuestionService {
    void addQuestion(String question, Question.QuestionOptions options, Integer categoryId, QuestionDifficulty difficulty);

    void changeCategory(Integer questionId, Integer categoryId);

    void answerQuestion(Integer userId, Integer questionId, Integer answer);
}
