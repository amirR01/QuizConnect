package com.games.QuizConnect.model.dto.response;

import com.games.QuizConnect.model.entity.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ViewPlayerResponseDTO {
    private Integer id;
    private String name;
    private Long score;
    private List<String> followedPlayers = new ArrayList<>();

    public static ViewPlayerResponseDTO fromUser(User user) {
        ViewPlayerResponseDTO dto = new ViewPlayerResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getUsername());
        dto.setScore(user.getPlayerDetails().getScore());
        for (User followedPlayer : user.getPlayerDetails().getFollowedPlayers()) {
            dto.getFollowedPlayers().add(followedPlayer.getUsername());
        }
        return dto;
    }

}
