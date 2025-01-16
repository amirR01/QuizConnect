package com.games.QuizConnect.controller;

import com.games.QuizConnect.model.dto.request.CreateUserRequestDTO;
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
    // TODO: use Spring Security to authenticate users and store current user in the session

    final private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<?> addUser(@RequestBody CreateUserRequestDTO createUserDTO) {
        createUserDTO.validate();
        try {
            userService.addUser(createUserDTO.getUsername(), createUserDTO.getPassword(), createUserDTO.getUserType());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }


}
