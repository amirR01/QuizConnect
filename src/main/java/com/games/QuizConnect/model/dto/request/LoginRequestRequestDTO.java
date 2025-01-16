package com.games.QuizConnect.model.dto.request;

import com.games.QuizConnect.model.BaseRequestDTO;
import lombok.Data;

@Data
public class LoginRequestRequestDTO extends BaseRequestDTO {
    private String username;
    private String password;

    public void validate() {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username is empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password is empty");
        }
    }
}
