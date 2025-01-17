package com.games.QuizConnect.model.dto.response;

import com.games.QuizConnect.model.entity.User;
import lombok.Data;

@Data
public class ViewDesignersResponseDTO {
    private Integer id;
    private String name;

    public static ViewDesignersResponseDTO fromUser(User user) {
        ViewDesignersResponseDTO dto = new ViewDesignersResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getUsername());
        return dto;
    }
}
