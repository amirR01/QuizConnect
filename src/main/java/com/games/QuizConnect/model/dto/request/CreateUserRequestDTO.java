package com.games.QuizConnect.model.dto.request;

import com.games.QuizConnect.model.BaseDTO;
import com.games.QuizConnect.model.enums.UserType;
import lombok.Data;

@Data
public class CreateUserRequestDTO extends BaseDTO {

    private String username;

    private String password;

    private UserType userType;

    public void validate() {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (userType == null) {
            throw new IllegalArgumentException("User type cannot be empty");
        }
    }
}
