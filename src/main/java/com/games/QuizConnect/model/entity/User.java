package com.games.QuizConnect.model.entity;

import com.games.QuizConnect.model.BaseEntity;
import com.games.QuizConnect.model.enums.UserType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Users")
public class User extends BaseEntity {

    private String username;

    // TODO: password should be encrypted
    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Embedded
    private PlayerDetails playerDetails = null;

    @Embedded
    private DesignerDetails designerDetails = null;

    public void addScore(Long score) {
        playerDetails.setScore(playerDetails.getScore() + score);
    }

    @Embeddable
    @Data
    public static class PlayerDetails {
        @Column(nullable = false)
        private Long score = 0L;

        @ManyToMany(targetEntity = User.class)
        @JoinTable(
                name = "user_followed_players",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "player_id")
        )
        private List<User> followedPlayers = new ArrayList<>();

        @ManyToMany(targetEntity = User.class)
        @JoinTable(
                name = "user_followed_designers",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "designer_id")
        )
        private List<User> followedDesigners = new ArrayList<>();
    }

    @Embeddable
    @Data
    public static class DesignerDetails {
        @OneToMany(targetEntity = Question.class, mappedBy = "designer")
        private List<Question> createdQuestions = new ArrayList<>();
    }

    public static final String userIdHeader = "userId";
}


