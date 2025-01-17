package com.games.QuizConnect.model.dto.response;

import com.games.QuizConnect.model.entity.User;
import com.games.QuizConnect.model.enums.UserType;
import lombok.Data;

@Data
public class LoginResponseDTO {
    private Integer id;
    private UserType userType;

    public static LoginResponseDTO fromUser(User user) {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setId(user.getId());
        loginResponseDTO.setUserType(user.getUserType());
        return loginResponseDTO;
    }
}
