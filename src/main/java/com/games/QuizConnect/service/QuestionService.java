package com.games.QuizConnect.service;

import com.games.QuizConnect.model.entity.Question;
import com.games.QuizConnect.model.enums.QuestionDifficulty;

import java.util.List;

public interface QuestionService {
    void addQuestion(String question, Question.QuestionOptions options, Integer categoryId, Integer designerId, QuestionDifficulty difficulty);

    void changeCategory(Integer questionId, Integer categoryId);

    void answerQuestion(Integer userId, Integer questionId, Integer answer);

    List<Question> getAllQuestionsByDesignerId(Integer designerId, Boolean attemptable);

    List<Question> getAllQuestionsByCategoryId(Integer categoryId, Boolean attemptable);

    Question getRandomQuestionByCategoryId(Integer categoryId, Boolean attemptable);

    Question getQuestionById(Integer questionId);

    Question getRandomAttemptableQuestion();
}
