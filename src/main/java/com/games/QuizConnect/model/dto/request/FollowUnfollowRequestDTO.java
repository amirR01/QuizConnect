package com.games.QuizConnect.model.dto.request;

import com.games.QuizConnect.model.BaseRequestDTO;
import lombok.Data;

@Data
public class FollowUnfollowRequestDTO extends BaseRequestDTO {

    private String username;

    private Boolean follow;

    public void validate() {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (follow == null) {
            throw new IllegalArgumentException("Follow cannot be empty");
        }
    }

}
