package com.games.QuizConnect.controller;

import com.games.QuizConnect.model.BaseResponseDTO;
import com.games.QuizConnect.model.dto.request.CreateQuestionRequestRequestDTO;
import com.games.QuizConnect.model.dto.request.GetQuestionsRequestDTO;
import com.games.QuizConnect.model.dto.response.IdResponseDTO;
import com.games.QuizConnect.model.dto.response.ViewQuestionResponseDTO;
import com.games.QuizConnect.model.entity.Question;
import com.games.QuizConnect.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.games.QuizConnect.model.entity.User.userIdHeader;

@RestController
@RequestMapping("/question/designer")
public class QuestionDesignerController {

    private final QuestionService questionService;

    @Autowired
    public QuestionDesignerController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<BaseResponseDTO<?>> addQuestion(
            @RequestHeader(userIdHeader) Integer userId,
            @RequestBody CreateQuestionRequestRequestDTO createQuestionRequestDTO
    ) {
        createQuestionRequestDTO.validate();
        if (userId == null) {
            return ResponseEntity.badRequest().body(BaseResponseDTO.error("User ID is required"));
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
            return ResponseEntity.ok(BaseResponseDTO.ok(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponseDTO.error(e.getMessage()));
        }
    }

    // TODO: add pagination
    // TODO: use Spring Security to authenticate users and store current user in the session
    @PostMapping(value = "/all", consumes = "application/json")
    public ResponseEntity<BaseResponseDTO<?>> getMyQuestions(
            @RequestHeader(userIdHeader) Integer userId,
            @RequestBody GetQuestionsRequestDTO getQuestionsRequestDTO
    ) {
        if (userId == null) {
            return ResponseEntity.badRequest().body(BaseResponseDTO.error("User ID is required"));
        }
        try {
            List<Question> questions = questionService.getAllQuestions(
                    userId,
                    getQuestionsRequestDTO.getCategoryId(),
                    getQuestionsRequestDTO.getDifficulty(),
                    false,
                    null
            );
            List<ViewQuestionResponseDTO> response = questions.stream().map(ViewQuestionResponseDTO::fromQuestionForDesigner).toList();
            return ResponseEntity.ok(BaseResponseDTO.ok(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponseDTO.error(e.getMessage()));
        }
    }


}
