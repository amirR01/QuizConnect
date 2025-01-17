package com.games.QuizConnect.controller;

import com.games.QuizConnect.model.BaseResponseDTO;
import com.games.QuizConnect.model.dto.request.CreateUserRequestRequestDTO;
import com.games.QuizConnect.model.dto.request.FollowUnfollowRequestDTO;
import com.games.QuizConnect.model.dto.request.LoginRequestRequestDTO;
import com.games.QuizConnect.model.dto.response.IdResponseDTO;
import com.games.QuizConnect.model.dto.response.LoginResponseDTO;
import com.games.QuizConnect.model.dto.response.ViewDesignersResponseDTO;
import com.games.QuizConnect.model.dto.response.ViewPlayerResponseDTO;
import com.games.QuizConnect.model.entity.User;
import com.games.QuizConnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.games.QuizConnect.model.entity.User.userIdHeader;

@RestController
@RequestMapping("/user")
public class UserController {
    final private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<BaseResponseDTO<?>> loginUser(@RequestBody LoginRequestRequestDTO createUserDTO) {
        createUserDTO.validate();
        try {
            User user = userService.login(createUserDTO.getUsername(), createUserDTO.getPassword());
            return ResponseEntity.ok(BaseResponseDTO.ok(LoginResponseDTO.fromUser(user)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponseDTO.error(e.getMessage()));
        }
    }

    @PostMapping(value = "/sign-up", consumes = "application/json")
    public ResponseEntity<?> addUser(@RequestBody CreateUserRequestRequestDTO createUserDTO) {
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

    @PostMapping(value = "/logout", consumes = "application/json")
    public ResponseEntity<BaseResponseDTO<?>> logoutUser(@RequestBody LoginRequestRequestDTO createUserDTO) {
        // TODO: implement
        return ResponseEntity.ok(BaseResponseDTO.ok("Logged out"));
    }

    @GetMapping(value = "/designer/all")
    public ResponseEntity<BaseResponseDTO<?>> getAllDesigners() {
        try {
            List<ViewDesignersResponseDTO> response = userService.getAllDesigners().stream()
                    .map(ViewDesignersResponseDTO::fromUser).toList();
            return ResponseEntity.ok(BaseResponseDTO.ok(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponseDTO.error(e.getMessage()));
        }
    }

    @GetMapping(value = "/player/all")
    public ResponseEntity<BaseResponseDTO<?>> getAllPlayers() {
        try {
            List<ViewPlayerResponseDTO> response = userService.getAllPlayers().stream()
                    .map(ViewPlayerResponseDTO::fromUser).toList();
            return ResponseEntity.ok(BaseResponseDTO.ok(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponseDTO.error(e.getMessage()));
        }
    }

    @PostMapping(value = "/player/follow-unfollow-player", consumes = "application/json")
    public ResponseEntity<BaseResponseDTO<?>> followUnfollowPlayer(
            @RequestHeader(userIdHeader) Integer userId,
            @RequestBody FollowUnfollowRequestDTO followUnfollowRequestDTO
    ) {
        followUnfollowRequestDTO.validate();
        try {
            if (followUnfollowRequestDTO.getFollow()) {
                userService.followPlayer(userId, followUnfollowRequestDTO.getUsername());
            } else {
                userService.unfollowPlayer(userId, followUnfollowRequestDTO.getUsername());
            }
            return ResponseEntity.ok(BaseResponseDTO.ok(""));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponseDTO.error(e.getMessage()));
        }
    }
}
