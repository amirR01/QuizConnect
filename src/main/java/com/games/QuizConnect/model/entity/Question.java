package com.games.QuizConnect.model.entity;

import com.games.QuizConnect.model.BaseEntity;
import com.games.QuizConnect.model.enums.QuestionDifficulty;
import com.games.QuizConnect.utils.QuestionOptionsConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Question extends BaseEntity {

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User designer;

    private String question;

    @Convert(converter = QuestionOptionsConverter.class)
    @ColumnTransformer(write = "?::jsonb")
    @Column(columnDefinition = "jsonb")
    private QuestionOptions options;

    private Integer correctOption;

    @ManyToOne(targetEntity = Category.class)
    private Category category;

    @Enumerated(EnumType.STRING)
    private QuestionDifficulty difficulty;

    private String explanation;

    public record QuestionOptions(String option1, String option2, String option3,
                                  String option4) {

        public String getByIndex(int index) {
            return switch (index) {
                case 1 -> option1;
                case 2 -> option2;
                case 3 -> option3;
                case 4 -> option4;
                default -> throw new IllegalArgumentException("Invalid index: " + index);
            };
        }

    }
}