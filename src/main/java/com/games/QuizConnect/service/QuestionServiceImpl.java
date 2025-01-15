package com.games.QuizConnect.service;

import com.games.QuizConnect.model.entity.Category;
import com.games.QuizConnect.model.entity.Question;
import com.games.QuizConnect.model.entity.User;
import com.games.QuizConnect.model.entity.UserQuestionAttempt;
import com.games.QuizConnect.model.enums.QuestionDifficulty;
import com.games.QuizConnect.model.enums.UserType;
import com.games.QuizConnect.repository.CategoryRepository;
import com.games.QuizConnect.repository.QuestionRepository;
import com.games.QuizConnect.repository.UserQuestionAttemptRepository;
import com.games.QuizConnect.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.games.QuizConnect.utils.Utils.getRandomElement;

@Service
public class QuestionServiceImpl implements QuestionService {

    final private QuestionRepository questionRepository;
    final private CategoryRepository categoryRepository;
    final private UserRepository userRepository;
    final private UserQuestionAttemptRepository userQuestionAttemptRepository;

    @Autowired
    public QuestionServiceImpl(
            QuestionRepository questionRepository,
            CategoryRepository categoryRepository,
            UserRepository userRepository,
            UserQuestionAttemptRepository userQuestionAttemptRepository) {
        this.questionRepository = questionRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.userQuestionAttemptRepository = userQuestionAttemptRepository;
    }

    @Transactional
    public void addQuestion(String question,
                            Question.QuestionOptions options,
                            Integer correctOption,
                            Integer categoryId, Integer designerId, QuestionDifficulty difficulty) {
        User designer = userRepository.findById(designerId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("Category not found"));
        if (designer.getUserType() != UserType.DESIGNER) {
            throw new IllegalArgumentException("User is not a designer");
        }

        Question newQuestion = new Question();
        newQuestion.setQuestion(question);
        newQuestion.setOptions(options);
        newQuestion.setDesigner(designer);
        newQuestion.addCategory(category);
        newQuestion.setDifficulty(difficulty);

        questionRepository.saveAndFlush(newQuestion);
    }

    public void changeCategory(Integer questionId, Integer categoryId) {

    }

    @Transactional
    public void answerQuestion(Integer userId, Integer questionId, Integer answer) {
        User player = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new IllegalArgumentException("Question not found"));
        if (player.getUserType() != UserType.PLAYER) {
            throw new IllegalArgumentException("User is not a player");
        }

        UserQuestionAttempt userQuestionAttempt = new UserQuestionAttempt();
        userQuestionAttempt.setUser(player);
        userQuestionAttempt.setQuestion(question);
        userQuestionAttempt.setChosenOption(answer);
        player.addScore(question.getDifficulty().getScore());

        userQuestionAttemptRepository.saveAndFlush(userQuestionAttempt);
        userRepository.saveAndFlush(player);
    }

    public List<Question> getAllQuestionsByDesignerId(Integer designerId, Boolean attemptable) {
        User designer = userRepository.findById(designerId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (designer.getUserType() != UserType.DESIGNER) {
            throw new IllegalArgumentException("User is not a designer");
        }
        if (attemptable) {
            return questionRepository.findNotAttemptedQuestions(designerId, null);
        } else {
            return questionRepository.findAllByDesignerId(designerId);
        }
    }

    public List<Question> getAllQuestionsByCategoryId(Integer categoryId, Boolean attemptable) {
        if (attemptable) {
            return questionRepository.findNotAttemptedQuestions(null, categoryId);
        } else {
            return questionRepository.findAllByCategoryId(categoryId);
        }
    }

    public Question getRandomQuestionByCategoryId(Integer categoryId, Boolean attemptable) {
        List<Question> questionList = getAllQuestionsByCategoryId(categoryId, attemptable);
        if (questionList.size() == 0) {
            return null;
        }
        return getRandomElement(questionList);
    }

    public Question getQuestionById(Integer questionId) {
        return questionRepository.findById(questionId).orElseThrow(()
                -> new IllegalArgumentException("Question not found"));
    }

    @Override
    public Question getRandomAttemptableQuestion() {
        List<Question> questionList = questionRepository.findNotAttemptedQuestions(null, null);
        if (questionList.size() == 0) {
            return null;
        }
        return getRandomElement(questionList);
    }

}

