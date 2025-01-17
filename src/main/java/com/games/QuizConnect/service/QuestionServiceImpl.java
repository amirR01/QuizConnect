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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Integer addQuestion(String question,
                               Question.QuestionOptions options,
                               Integer correctOption,
                               Integer categoryId, Integer designerId, QuestionDifficulty difficulty) {
        User designer = userRepository.findById(designerId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (designer.getUserType() != UserType.DESIGNER) {
            throw new IllegalArgumentException("User is not a designer");
        }
        Category category = null;
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("Category not found"));
        }

        Question newQuestion = new Question();
        newQuestion.setQuestion(question);
        newQuestion.setOptions(options);
        newQuestion.setDesigner(designer);
        if (category != null) {
            newQuestion.setCategory(category);
        }
        newQuestion.setDifficulty(difficulty);
        newQuestion.setCorrectOption(correctOption);

        newQuestion = questionRepository.saveAndFlush(newQuestion);
        return newQuestion.getId();
    }

    public void changeCategory(Integer questionId, Integer categoryId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new IllegalArgumentException("Question not found"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("Category not found"));
        question.setCategory(category);
        questionRepository.saveAndFlush(question);
    }

    @Transactional
    public boolean answerQuestion(Integer userId, Integer questionId, Integer answer) {
        User player = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new IllegalArgumentException("Question not found"));
        if (player.getUserType() != UserType.PLAYER) {
            throw new IllegalArgumentException("User is not a player");
        }

        boolean correct = question.getCorrectOption().equals(answer);

        UserQuestionAttempt userQuestionAttempt = new UserQuestionAttempt();
        userQuestionAttempt.setUser(player);
        userQuestionAttempt.setQuestion(question);
        userQuestionAttempt.setChosenOption(answer);

        if (correct) {
            player.addScore(question.getDifficulty().getScore());
        }
        userQuestionAttemptRepository.saveAndFlush(userQuestionAttempt);
        userRepository.saveAndFlush(player);
        return correct;
    }

    @Override
    public Question getQuestionById(Integer questionId) {
        return null;
    }

    @Transactional(readOnly = true)
    public List<Question> getAllQuestions(
            Integer designerId,
            Integer categoryId,
            QuestionDifficulty difficulty,
            Boolean attemptable,
            Integer playerId) {

        // TODO: This method can be removed since inputs not being valid only affects the return value
        checkInputValuesForGetQuestionsMethod(designerId, categoryId, playerId);

        if (attemptable) {
            if (playerId == null) {
                throw new IllegalArgumentException("Player id cannot be empty");
            }
            String difficultyString = difficulty == null ? null : difficulty.toString();
            return questionRepository.findNotAttemptedQuestions(playerId, designerId, categoryId, difficultyString);
        } else {
            return questionRepository.findAllQuestions(designerId, categoryId, difficulty);
        }
    }

    @Transactional(readOnly = true)
    public Question getRandomQuestion(
            Integer designerId,
            Integer categoryId,
            QuestionDifficulty difficulty,
            Boolean attemptable,
            Integer playerId) {
        List<Question> questionList = getAllQuestions(designerId, categoryId, difficulty, attemptable, playerId);
        if (questionList.size() == 0) {
            return null;
        }
        return getRandomElement(questionList);
    }

    private void checkInputValuesForGetQuestionsMethod(Integer designerId, Integer categoryId, Integer playerId) {
        if (designerId != null) {
            User designer = userRepository.findById(designerId).orElseThrow(() -> new IllegalArgumentException("User not found"));
            if (designer.getUserType() != UserType.DESIGNER) {
                throw new IllegalArgumentException("User is not a designer");
            }
        }
        if (categoryId != null) {
            categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("Category not found"));
        }
        if (playerId != null) {
            User player = userRepository.findById(playerId).orElseThrow(() -> new IllegalArgumentException("User not found"));
            if (player.getUserType() != UserType.PLAYER) {
                throw new IllegalArgumentException("User is not a player");
            }
        }
    }
}

