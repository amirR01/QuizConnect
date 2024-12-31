package com.games.QuizConnect.model.entity;

import com.games.QuizConnect.model.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {

    @Id
    private Integer id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Embedded
    private PlayerDetails playerDetails;

    @Embedded
    private DesignerDetails designerDetails;

    @Embeddable
    public static class PlayerDetails {
        private Long score;

        @ManyToMany(targetEntity = Question.class)
        @JoinTable(
                name = "user_attempted_questions",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "question_id")
        )
        private List<Question> attemptedQuestions;

        @ManyToMany(targetEntity = User.class)
        @JoinTable(
                name = "user_followed_players",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "player_id")
        )
        private List<User> followedPlayers;

        @ManyToMany(targetEntity = User.class)
        @JoinTable(
                name = "user_followed_designers",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "designer_id")
        )
        private List<User> followedDesigners;
    }

    @Embeddable
    public static class DesignerDetails {
        @OneToMany(targetEntity = Question.class, mappedBy = "designer")
        private List<Question> createdQuestions;
    }
}


