package com.games.QuizConnect.controller;

import com.games.QuizConnect.model.dto.request.CreateUserRequestDTO;
import com.games.QuizConnect.model.dto.request.LoginRequestDTO;
import com.games.QuizConnect.model.dto.response.IdResponseDTO;
import com.games.QuizConnect.model.dto.response.LoginResponseDTO;
import com.games.QuizConnect.model.entity.User;
import com.games.QuizConnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    final private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO createUserDTO) {
        createUserDTO.validate();
        try {
            User user = userService.login(createUserDTO.getUsername(), createUserDTO.getPassword());
            return ResponseEntity.ok(LoginResponseDTO.fromUser(user));
        } catch (Exception e) {
            LoginResponseDTO response = new LoginResponseDTO();
            response.setStatus(false);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value = "/sign-up", consumes = "application/json")
    public ResponseEntity<?> addUser(@RequestBody CreateUserRequestDTO createUserDTO) {
        createUserDTO.validate();
        try {
            Integer userId = userService.addUser(createUserDTO.getUsername(), createUserDTO.getPassword(), createUserDTO.getUserType());
            IdResponseDTO response = new IdResponseDTO();
            response.setId(userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
