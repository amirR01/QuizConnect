package com.games.QuizConnect.model.entity;

import com.games.QuizConnect.model.BaseEntity;
import com.games.QuizConnect.model.enums.QuestionDifficulty;
import com.games.QuizConnect.utils.QuestionOptionsConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Question extends BaseEntity {

    @ManyToOne(targetEntity = User.class)
    private User designer;

    private String question;

    @Convert(converter = QuestionOptionsConverter.class)
    @Column(columnDefinition = "jsonb")
    private QuestionOptions options;

    private Integer answer;

    @ManyToMany(targetEntity = Category.class)
    @JoinTable(
            name = "question_category",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> category;

    private QuestionDifficulty difficulty;

    private String explanation;

    public static class QuestionOptions {
        private String option1;
        private String option2;
        private String option3;
        private String option4;

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
    public void addCategory(Category category) {
        this.category.add(category);
    }
}