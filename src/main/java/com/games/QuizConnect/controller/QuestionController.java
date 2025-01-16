package com.games.QuizConnect.controller;

import com.games.QuizConnect.model.dto.request.CreateQuestionRequestRequestDTO;
import com.games.QuizConnect.model.dto.request.GetQuestionsRequestDTO;
import com.games.QuizConnect.model.dto.response.IdResponseDTO;
import com.games.QuizConnect.model.dto.response.ViewQuestionResponseDto;
import com.games.QuizConnect.model.entity.Question;
import com.games.QuizConnect.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.games.QuizConnect.model.entity.User.userIdHeader;

@RestController
@RequestMapping("/question")
public class QuestionController {
    // TODO: use Spring Security to authenticate users and store current user in the session

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<?> addQuestion(
            @RequestHeader(userIdHeader) Integer userId,
            @RequestBody CreateQuestionRequestRequestDTO createQuestionRequestDTO
    ) {
        createQuestionRequestDTO.validate();
        if (userId == null) {
            return ResponseEntity.badRequest().body("User ID is required");
        }
        try {
            Integer questionId = questionService.addQuestion(
                    createQuestionRequestDTO.getQuestion(),
                    createQuestionRequestDTO.getQuestionOptions(),
                    createQuestionRequestDTO.getCorrectOption(),
                    createQuestionRequestDTO.getCategoryId(),
                    userId,
                    createQuestionRequestDTO.getDifficulty()
            );
            IdResponseDTO response = new IdResponseDTO();
            response.setId(questionId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // TODO: add pagination
    @PostMapping(value = "/my-questions", consumes = "application/json")
    public ResponseEntity<?> getMyQuestions(
            @RequestHeader(userIdHeader) Integer userId,
            @RequestBody GetQuestionsRequestDTO getQuestionsRequestDTO
    ) {
        if (userId == null) {
            return ResponseEntity.badRequest().body("User ID is required");
        }
        try {
            List<Question> questions = questionService.getAllQuestions(
                    userId,
                    getQuestionsRequestDTO.getCategoryId(),
                    getQuestionsRequestDTO.getDifficulty(),
                    false,
                    null
            );
            return ResponseEntity.ok(questions.stream().map(ViewQuestionResponseDto::fromQuestion).toList());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
