package com.games.QuizConnect.service;

import com.games.QuizConnect.model.entity.Question;
import com.games.QuizConnect.model.enums.QuestionDifficulty;

import java.util.List;

public interface QuestionService {
    Integer addQuestion(String question,
                        Question.QuestionOptions options,
                        Integer correctOption,
                        Integer categoryId, Integer designerId, QuestionDifficulty difficulty);

    void changeCategory(Integer questionId, Integer categoryId);

    boolean answerQuestion(Integer userId, Integer questionId, Integer answer);

    Question getQuestionById(Integer questionId);

    List<Question> getAllQuestions(
            Integer designerId,
            Integer categoryId,
            QuestionDifficulty difficulty,
            Boolean attemptable,
            Integer playerId);

    Question getRandomQuestion(
            Integer designerId,
            Integer categoryId,
            QuestionDifficulty difficulty,
            Boolean attemptable,
            Integer playerId);
}
