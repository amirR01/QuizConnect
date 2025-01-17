package com.games.QuizConnect.controller;

import com.games.QuizConnect.model.BaseResponseDTO;
import com.games.QuizConnect.model.dto.request.AnswerQuestionRequestDTO;
import com.games.QuizConnect.model.dto.request.GetQuestionsRequestDTO;
import com.games.QuizConnect.model.dto.response.AnswerQuestionResponseDTO;
import com.games.QuizConnect.model.dto.response.ViewQuestionResponseDTO;
import com.games.QuizConnect.model.entity.Question;
import com.games.QuizConnect.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.games.QuizConnect.model.entity.User.userIdHeader;

@RestController
@RequestMapping("/question/player")
public class QuestionPlayerController {
    private final QuestionService questionService;

    @Autowired
    public QuestionPlayerController(QuestionService questionService) {
        this.questionService = questionService;
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
                    getQuestionsRequestDTO.getDesignerId(),
                    getQuestionsRequestDTO.getCategoryId(),
                    getQuestionsRequestDTO.getDifficulty(),
                    true,
                    userId
            );
            List<ViewQuestionResponseDTO> response = questions.stream().map(ViewQuestionResponseDTO::fromQuestionForPlayer).toList();
            return ResponseEntity.ok(BaseResponseDTO.ok(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponseDTO.error(e.getMessage()));
        }
    }

    @PostMapping(value = "/answer", consumes = "application/json")
    public ResponseEntity<BaseResponseDTO<?>> answerQuestion(
            @RequestHeader(userIdHeader) Integer userId,
            @RequestBody AnswerQuestionRequestDTO answerQuestionRequestDTO
    ) {
        if (userId == null) {
            return ResponseEntity.badRequest().body(BaseResponseDTO.error("User ID is required"));
        }
        try {
            boolean correct = questionService.answerQuestion(
                    userId,
                    answerQuestionRequestDTO.getQuestionId(),
                    answerQuestionRequestDTO.getAnswer()
            );
            return ResponseEntity.ok(BaseResponseDTO.ok(new AnswerQuestionResponseDTO(correct)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponseDTO.error(e.getMessage()));
        }
    }


}
